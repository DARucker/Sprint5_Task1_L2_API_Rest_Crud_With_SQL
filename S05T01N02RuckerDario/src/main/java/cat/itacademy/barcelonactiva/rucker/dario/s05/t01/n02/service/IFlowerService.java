package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;

import java.util.List;

public interface IFlowerService {

    List<Flowerdto> listAll();
    Flowerdto create (Flowerdto flowerdto);
    Flowerdto update (Flowerdto flowerdto);
    Flowerdto findById(int id);
    void delete(Flowerdto flowerdto);
}
