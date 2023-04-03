package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IFlowerService {

    List<Flowerdto> listAll();
    Flowerdto create (Flowerdto flowerdto);
    Flowerdto update (Flowerdto flowerdto, int id, BindingResult result);
    Flowerdto findById(int id);
    Flowerdto delete(int id);
    Flowerdto entityToDto(Flower flower);
    Flower dtoToEntity(Flowerdto flowerdto);
}
