package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.repository.FlowerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowerServiceImpl implements IFlowerService{

    @Autowired
    private FlowerRepository flowerRepository;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public List<Flowerdto> listAll() {
        return null;
    }

    @Override
    public Flower save(Flower flower) {
        return flowerRepository.save(flower);
    }

    @Override
    public Flower update(Flower flower) {
        return null;
    }

    @Override
    public Flowerdto findById(int id) {
        return null;
    }

    @Override
    public void delete(Flowerdto flowerdto) {

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
