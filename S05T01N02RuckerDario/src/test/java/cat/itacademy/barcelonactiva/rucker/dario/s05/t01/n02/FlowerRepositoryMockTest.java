package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.repository.FlowerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static  org.assertj.core.api.Assertions.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FlowerRepositoryMockTest {

    @Autowired
    private FlowerRepository flowerRepository;

    @DisplayName("Testing save")
    @Test
    public void WhenSave_ThenReturnNotNull(){
        //given
        Flower flower = new Flower(1,"rosa", "Spain");
        //when
        Flower saved = flowerRepository.save(flower);
        //then
        assertNotNull(saved);
        assertThat(saved).isNotNull();
    }

    @Test
    public void WhenFindById_ThenReturnFlower(){
        Flower flower1 = new Flower(1,"rosa", "spain");
        flowerRepository.save(flower1);
        Flower found1 = flowerRepository.findById(1).orElse(null);
        assertTrue(found1.getCountry().equalsIgnoreCase("Spain"));
        assertThat(found1.getName()).isEqualTo("rosa");

    }
    @DisplayName("Test that the size of db is 1 record larger after saving a new record")
    @Test
    public void WhenList_ThenReturnSize(){
        List<Flower> flowerList1 = flowerRepository.findAll();
        Flower flower2 = new Flower(2,"clavel", "france");
        flowerRepository.save(flower2);
        List<Flower>flowerList2 = flowerRepository.findAll();
        assertThat(flowerList1.size() + 1).isEqualTo(flowerList2.size());
    }

}
