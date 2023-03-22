package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.repository.FlowerRepository;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.FlowerServiceImpl;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.IFlowerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
@SpringBootTest
public class FlowerServiceMockTest {


    @Mock
    private FlowerRepository flowerRepository;

    private IFlowerService flowerService;

    private Flower flower01;
    //public BranchServiceMockTest() {
    //}

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        flowerService = new FlowerServiceImpl(flowerRepository);

        // Given
        flower01 = Flower.builder()
                .id(2)
                .name("ceibo")
                .country("Argentina")
                .build();
        // When
        Mockito.when(flowerRepository.save(flower01)).thenReturn(flower01);
        Mockito.when(flowerRepository.findById(1)).thenReturn(Optional.of(flower01));
    }

    @DisplayName("Test findById method")
    @Test
    public void whenFindById_ThenReturnBrach () {

        Flowerdto found = flowerService.findById(1);
        // Then
        Assertions.assertThat(found.getName()).isEqualTo("clavel");
    }

    @DisplayName("Test EntityToDto method")
    @Test
    public void WhenFindById_ThenTestEntityToDtoMethod() {
        Flowerdto found = flowerService.findById(1);
        Assertions.assertThat(found.getFlowerType()).isEqualTo("EU");
    }
    @DisplayName("Test save method")
    @Test
    public void WhenSaveFlower_ThenFlowerIsNotNull(){

        //Mockito.when(flowerRepository.save(flower01)).thenReturn(flower01);
        Flowerdto flowerdto01 = flowerService.entityToDto(flower01);
        Flowerdto saved = flowerService.create(flowerdto01);
        Assertions.assertThat(saved.getCountry()).isEqualTo("Argentina");
        Assertions.assertThat(saved).isNull();
    }
}
