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

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest
public class FlowerServiceMockTest {

    @Mock
    private FlowerRepository flowerRepository;
    private IFlowerService flowerService;
    private Flower flower01;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        flowerService = new FlowerServiceImpl(flowerRepository);

        flower01 = Flower.builder()
                .id(1)
                .name("ceibo")
                .country("Argentina")
                .build();
    }
    @DisplayName("Test findById method")
    @Test
    public void whenFindById_ThenReturnBrach() {
        // Given
        Mockito.when(flowerRepository.findById(1)).thenReturn(Optional.of(flower01));
        // When
        Flowerdto found = flowerService.findById(1);
        // Then
        Assertions.assertThat(found.getName()).isEqualTo("ceibo");
    }

    @DisplayName("Test EntityToDto method")
    @Test
    public void WhenFindById_ThenTestEntityToDtoMethod() {
        Mockito.when(flowerRepository.findById(2)).thenReturn(Optional.of(flower01));
        Flowerdto found = flowerService.findById(2);
        Assertions.assertThat(found.getFlowerType()).isEqualTo("no EU");
    }

    @DisplayName("Test save method")
    @Test
    public void WhenSaveFlower_ThenFlowerIsNotNull() {
        Mockito.when(flowerRepository.save(flower01)).thenReturn(flower01);
        Flowerdto flowerdto01 = flowerService.entityToDto(flower01);
        Flowerdto saved = flowerService.create(flowerdto01);
        Assertions.assertThat(saved.getCountry()).isEqualTo("Argentina");
        Assertions.assertThat(saved).isNotNull();
    }

    @DisplayName("Test listing method")
    @Test
    public void WhenListFlower_ThenObteinFlowerList() {
        Flower flower02 = Flower.builder()
                .id(3)
                .name("Tulipan")
                .country("Holand")
                .build();
        // Given
        Mockito.when(flowerRepository.findAll()).thenReturn(List.of(flower01, flower02));
        // When
        List<Flowerdto> flowerList = flowerService.listAll();
        // Then
        Assertions.assertThat(flowerList).isNotNull();
        Assertions.assertThat(flowerList.size()).isEqualTo(2);
    }

    @DisplayName("Test update method")
    @Test
    public void WhenUpdateFlower_ThenGetFlowerUpdated() {

        // Given
        Mockito.when(flowerRepository.save(flower01)).thenReturn(flower01);
        Mockito.when(flowerRepository.findById(1)).thenReturn(Optional.of(flower01));
        //
        flower01.setName("flor de ceibo");
        flower01.setCountry("Uruguay");
        Flowerdto flowerdto04 = flowerService.entityToDto(flower01);
        // When
        Flowerdto flowerUpdated = flowerService.update(flowerdto04);
        Assertions.assertThat(flowerUpdated).isNotNull();
        Assertions.assertThat(flowerUpdated.getCountry()).isEqualTo("Uruguay");
    }

    @Test
    public void WhenSendEntity_ThenReturnDto(){

        Flower flower04 = Flower.builder()
                .id(3)
                .name("Tulipan")
                .country("Netherlands")
                .build();
        Flowerdto flowerdto04 = flowerService.entityToDto(flower04);
        Assertions.assertThat(flowerdto04).isNotNull();
        Assertions.assertThat(flowerdto04.getFlowerType()).isEqualTo("EU");
    }
    @Test
    public void WhenSendDto_ThenReturnEntity(){
        Flowerdto flowerdto05 = Flowerdto.builder()
                .id(5)
                .name("Loto blanco")
                .country("Korea")
                .flowerType("no EU")
                .build();
        Flower flower05 = flowerService.dtoToEntity(flowerdto05);
        Assertions.assertThat(flower05).isNotNull();
        Assertions.assertThat(flower05.getName()).isEqualToIgnoringCase("loto blanco");
    }
}