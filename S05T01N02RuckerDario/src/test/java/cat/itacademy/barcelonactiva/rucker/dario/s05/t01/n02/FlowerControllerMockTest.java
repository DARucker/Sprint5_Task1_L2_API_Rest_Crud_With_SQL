package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.controller.FlowerController;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.IFlowerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(FlowerController.class)
public class FlowerControllerMockTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private IFlowerService flowerService;


    @Test
    public void testSaveFlower() throws Exception {
    Flowerdto flowerdto1 = Flowerdto.builder()
            .id(1)
            .name("ceibo")
            .country("argentina")
            .flowerType("UE")
            .build();

        given(flowerService.create(any(Flowerdto.class)))
                .willAnswer((invocation -> invocation.getArgument(0)));
        ResultActions response = mockMvc.perform(post("/flower/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flowerdto1)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("ceibo")))
                .andExpect(jsonPath("$.country", is("argentina")));
    }
    @Test
    public void testGetOneFlower() throws Exception {
        Flowerdto flowerdto1 = Flowerdto.builder()
                .id(1)
                .name("ceibo")
                .country("argentina")
                .flowerType("UE")
                .build();

        given(flowerService.findById(1)).willReturn(flowerdto1);
        ResultActions response = mockMvc.perform(get("/flower/getOne/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flowerdto1)));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("ceibo")))
                .andExpect(jsonPath("$.country", is("argentina")));
    }

    @Test
    public void testGetAllFlowers() throws Exception {
        List<Flowerdto> flowerdtoList = new ArrayList<>();
        flowerdtoList.add(Flowerdto.builder().name("Loto").country("japan").flowerType("no EU").build());
        flowerdtoList.add(Flowerdto.builder().name("Cerezo").country("Spain").flowerType("EU").build());

        given(flowerService.listAll()).willReturn(flowerdtoList);
        ResultActions response = mockMvc.perform(get("/flower/getAll"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(2)));
    }
}
