package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.controller;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.IFlowerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flower")
@Tag(name = "Spring 5 - Task 1 - Level 2", description = "Rest CRUD with SQL database")
public class FlowerController {

    private static Logger LOG = LoggerFactory.getLogger(FlowerController.class);
    @Autowired
    private IFlowerService flowerService;
    Function<Integer, ResponseStatusException> notFoundId = (id) -> {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "No Employee Available with the given Id - " + id);
    };

    @PostMapping(value = "/add")
    @Operation(summary = "Create a new flower", description = "Add a new flower into the database")
    @ApiResponse(responseCode = "201", description = "Flower created correctly")
    public ResponseEntity<Flowerdto> create(@Valid @RequestBody Flowerdto flowerdto, BindingResult result){
        LOG.info("Using method: createFlower " + flowerdto);
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(flowerService.create(flowerdto));
    }

    @Operation(summary = "Find a flower", description = "Find the selected flower using the id as the key search")
    @ApiResponse(responseCode = "200", description = "Flower found")
    @ApiResponse(responseCode = "404", description = "Flower not found")
    @GetMapping(value = "getOne/{id}")
    public ResponseEntity<Flowerdto> findById (@PathVariable int id){
        LOG.info("Using method getFlower");
        Flowerdto flowerdto = flowerService.findById(id);
        if(flowerdto != null){
            return ResponseEntity.ok(flowerdto);
        } else{
            throw (ResponseStatusException)this.notFoundId.apply(id);
        }
//       Flowerdto flowerdto = null;
//        try {
//            flowerdto = flowerService.findById(id);
//        } catch (ResponseStatusException e){
//            return new ResponseEntity<>(e.getHeaders(), e.getStatusCode());
//        }
//        return ResponseEntity.ok(flowerdto);
    }

    @GetMapping(value = "/getAll")
    public  ResponseEntity<List<Flowerdto>> getAll(){
        LOG.info("Using method getAll to list every item on DB");
        List<Flowerdto> allFlower = flowerService.listAll();
        if(allFlower.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allFlower);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Flowerdto> update(@Valid @RequestBody Flowerdto flowerdto, BindingResult result, @PathVariable int id){
        LOG.info("Using method: updateFlower " + flowerdto);
        try{
            Flowerdto flowerDBdto = flowerService.update(flowerdto);
            flowerdto.setId(id);
            flowerDBdto = flowerService.update(flowerdto);
            return ResponseEntity.ok(flowerDBdto);
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getHeaders(), e.getStatusCode());
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Flowerdto> delete(@PathVariable int id){
        LOG.info("Using method delete");
    Flowerdto flowerDelete = flowerService.findById(id);
    if(flowerDelete == null){
        return ResponseEntity.notFound().build();
    }
        flowerService.delete(flowerDelete.getId());
        return ResponseEntity.ok(flowerDelete);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = new ErrorMessage("01", errors);
        //ErrorMessage errorMessage = ErrorMessage.builder()
          //      .code("01")
            //    .customErrorMessages(errors)
              //  .build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }
}
