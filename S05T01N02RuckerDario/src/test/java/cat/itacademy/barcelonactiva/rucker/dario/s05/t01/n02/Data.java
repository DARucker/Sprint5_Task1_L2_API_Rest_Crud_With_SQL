package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;

import java.util.Optional;

public class Data {

    public static Optional<Flowerdto> createFlower1(){
        return Optional.of(new Flowerdto(1, "ceibo rojo", "argentina", "UE ok"));
    }
}
