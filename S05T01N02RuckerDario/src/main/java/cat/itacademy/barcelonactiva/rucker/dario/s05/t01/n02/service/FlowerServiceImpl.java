package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.repository.FlowerRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FlowerServiceImpl implements IFlowerService{

    private static final Logger log = LoggerFactory.getLogger(FlowerServiceImpl.class);
    @Autowired
    private FlowerRepository flowerRepository;

    public FlowerServiceImpl(FlowerRepository flowerRepository) {
        this.flowerRepository = flowerRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public Flowerdto create(Flowerdto flowerdto) {
        log.info("Creating flower with id {}", flowerdto.getId());
        Flower flower = dtoToEntity(flowerdto);
        flower = flowerRepository.save(flower);
        return entityToDto(flower);
    }

    @Override
    public Flowerdto findById(int id) throws ResponseStatusException {
        Optional<Flower> flower = flowerRepository.findById(id);
        if(flower.isPresent()){
            log.info("Flower found with id {} is: {}", id, flower.get());
            return entityToDto(flower.get());
        } else {
            log.info("No flower was found with id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No flower available with id: " + id);
        }
    }

    @Override
    public List<Flowerdto> listAll() {
        return flowerRepository.findAll().stream()
                .map(x -> entityToDto(x)) // Another option using reference method: .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Flowerdto update(Flowerdto flowerdto, int id, BindingResult result) {

        //Flower flower = dtoToEntity(flowerdto);
        Flowerdto flowerDtoDB = findById(id);
        if(flowerDtoDB == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No flower available with id: " + flowerdto.getId());
        }
        flowerDtoDB.setName(flowerdto.getName());
        flowerDtoDB.setCountry(flowerdto.getCountry());
        Flower flowerUpdate = dtoToEntity(flowerDtoDB);
        if(!result.hasErrors()) {
            flowerUpdate = flowerRepository.save(flowerUpdate);
        }
        return entityToDto(flowerUpdate);
    }

    @Override
    public Flowerdto delete(int id) {

        Flower flowerDelete = flowerRepository.findById(id).orElse(null);
        if(flowerDelete == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No flower available with id: " + id);
        }
        flowerRepository.delete(flowerDelete);
        return entityToDto(flowerDelete);
    }

    /**
     * This method transform an entity into a DTO
     * @param flower
     * @return Flowerdto
     */

    @Override
    public Flowerdto entityToDto(Flower flower){
        Flowerdto flowerdto = modelMapper().map(flower, Flowerdto.class);

        boolean eu = flowerdto.getCountrys().stream()
                .anyMatch(x -> x.equalsIgnoreCase(flower.getCountry()));
        if(eu){
            flowerdto.setFlowerType("EU");
        }else {
            flowerdto.setFlowerType("no EU");
        }
        return flowerdto;
    }

    /**
     * This method recives a DTO object to transform it into an entity
     * @param flowerdto
     * @return flower
     */
    @Override
    public Flower dtoToEntity (Flowerdto flowerdto){
        Flower flower = modelMapper().map(flowerdto, Flower.class);

        return flower;
    }
}