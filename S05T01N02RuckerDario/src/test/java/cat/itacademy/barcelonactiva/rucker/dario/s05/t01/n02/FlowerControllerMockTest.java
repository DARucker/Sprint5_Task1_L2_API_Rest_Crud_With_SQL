package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.controller.FlowerController;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.IFlowerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FlowerController.class)
public class FlowerControllerMockTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFlowerService flowerService;

    @Test
    public void testBranchController() throws Exception {
        when(flowerService.findById(1)).thenReturn(Data.createFlower1().orElseThrow());

        mockMvc.perform(get("/flower/update/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.country").value("argentina"))
                .andExpect(jsonPath("$.name").value("ceibo rojo"));

        verify(flowerService).findById(1);


    }
}
