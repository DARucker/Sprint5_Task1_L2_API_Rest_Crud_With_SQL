package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.repository.FlowerRepository;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.FlowerServiceImpl;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.IFlowerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class FlowerServiceMockTest {


    @Mock
    private FlowerRepository flowerRepository;

    private IFlowerService flowerService;

    //public BranchServiceMockTest() {
    //}

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        flowerService = new FlowerServiceImpl(flowerRepository);

        // Given
        Flower flower01 = Flower.builder()
                .id(1)
                .name("rosa")
                .country("France")
                .build();
        // When
        Mockito.when(flowerRepository.findById(1)).thenReturn(Optional.of(flower01));
    }

    @Test
    public void whenFindById_ThenReturnBrach () {

        Flowerdto found = flowerService.findById(1);
        // Then
        Assertions.assertThat(found.getName()).isEqualTo("Ar2");

    }

    @Test
    public void WhenFindById_ThenTestEntityToDtoMethod() {
        Flowerdto found = flowerService.findById(1);
        Assertions.assertThat(found.getFlowerType()).isEqualTo("EU");
    }


}
