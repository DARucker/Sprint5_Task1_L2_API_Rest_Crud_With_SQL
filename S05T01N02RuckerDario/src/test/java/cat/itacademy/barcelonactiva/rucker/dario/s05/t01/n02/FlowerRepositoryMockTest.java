package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.repository.FlowerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FlowerRepositoryMockTest {

    @Autowired
    private FlowerRepository flowerRepository;

    @Test
    public void WhenSave_ThenReturnNotNull(){
        Flower flower = new Flower(1,"rosa", "Spain");
        Flower found = flowerRepository.save(flower);
        assertNotNull(found);
    }

    @Test
    public void WhenFindById_ThenReturnFlower(){
        Flower flower1 = new Flower(1,"rosa", "spain");
        flowerRepository.save(flower1);
        Flower found1 = flowerRepository.findById(1).orElse(null);
        assertTrue(found1.getCountry().equalsIgnoreCase("Spain"));
        org.assertj.core.api.Assertions.assertThat(found1.getName()).isEqualTo("rosa");

    }
    @Test
    public void WhenList_ThenReturnSize(){
        Flower flower2 = new Flower(1,"rosa", "spain");
        flowerRepository.save(flower2);
        List<Flower> flowerList = flowerRepository.findAll();
        assertTrue(flowerList.size() == 2);
    }

}
