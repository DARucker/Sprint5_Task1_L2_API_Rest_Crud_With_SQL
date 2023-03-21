package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.repository.FlowerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FlowerServiceImpl implements IFlowerService{

    @Autowired
    private FlowerRepository flowerRepository;

    public FlowerServiceImpl(FlowerRepository flowerRepository) {
    this.flowerRepository = flowerRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override // DONE //
    public List<Flowerdto> listAll() {
        return flowerRepository.findAll().stream()
                .map(x -> entityToDto(x)) // Another option using reference method: .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override // DONE //
    public Flowerdto create(Flowerdto flowerdto) {
        Flower flower = dtoToEntity(flowerdto);
        flower = flowerRepository.save(flower);
        return entityToDto(flower);
    }

    @Override // DONE //
    public Flowerdto update(Flowerdto flowerdto) {
        Flowerdto flowerDB = findById(flowerdto.getId());
        if(flowerDB == null) {
            return null;
        }
        flowerDB.setName(flowerdto.getName());
        flowerDB.setCountry(flowerdto.getCountry());
        Flower flowerUpdate = dtoToEntity(flowerDB);
        flowerUpdate = flowerRepository.save(flowerUpdate);
        return entityToDto(flowerUpdate);
    }

    @Override // DONE //
    public Flowerdto findById(int id) {
        Flower flower = flowerRepository.findById(id).orElse(null);
        return entityToDto(flower);
    }

    @Override
    public Flowerdto delete(int id) {

        Flower flowerDelete = flowerRepository.findById(id).orElse(null);
        if(flowerDelete == null){
            return null;
        }
        return delete(flowerDelete.getId());
    }
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

    public Flower dtoToEntity (Flowerdto flowerdto){
        Flower flower = modelMapper().map(flowerdto, Flower.class);

        return flower;
    }


}
