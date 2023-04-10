package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.controller;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.IFlowerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    @Operation(summary = "Adds a new flower", description = "Add a new flower into the database")
    @ApiResponse(responseCode = "201", description = "Flower created correctly", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Flowerdto.class))})
    @ApiResponse(responseCode = "406", description = "Flower values not valid", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal Server Error while creating the flower", content = @Content)
    public ResponseEntity<?> create(@Valid @RequestBody Flowerdto flowerdto, BindingResult result){
        LOG.info("Using method: createFlower " + flowerdto);
        if(result.hasErrors()){
            Map<String, String> error = new HashMap<>();
            List<FieldError> errores = result.getFieldErrors();
            for(int i = 0; i < errores.size(); i++){
                String campo = errores.get(i).getField();
                String mensaje = errores.get(i).getDefaultMessage();
                error.put(errores.size() - i + ") Field: " + campo, "Message: " + mensaje);
                LOG.info("field {}",campo + "message {}", mensaje);
            }
            return new ResponseEntity<Map<String,String>>(error, HttpStatus.NOT_ACCEPTABLE);
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(flowerService.create(flowerdto));
    }

    @Operation(summary = "Retrive a flower using the flower id", description = "Find the selected flower using the id as the key search")
    @ApiResponse(responseCode = "200", description = "Flower found", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Flowerdto.class))})
    @ApiResponse(responseCode = "404", description = "Flower not found with supplied id",
            content = @Content)
    @GetMapping(value = "getOne/{id}")
    public ResponseEntity<?> findById (@PathVariable int id){
        LOG.info("Using method getFlower");
//        Flowerdto flowerdto = flowerService.findById(id);
//        if(flowerdto != null){
//            return ResponseEntity.ok(flowerdto);
//        } else{
//            throw (ResponseStatusException)this.notFoundId.apply(id);
//        }
       Flowerdto flowerdto = null;
        try {
            flowerdto = flowerService.findById(id);
        } catch (ResponseStatusException e){
            Map<String, Object> error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Reason", e.getReason());
            return new ResponseEntity<Map<String,Object>>(error, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(flowerdto);
    }

    @Operation(summary = "Retrives all flowers in database", description = "Find and retrives each flower existing in database")
    @ApiResponse(responseCode = "200", description = "Flowers found", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Flowerdto.class))})
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    @GetMapping(value = "/getAll")
    public  ResponseEntity<List<Flowerdto>> getAll(){
        LOG.info("Using method getAll to list every item on DB");
        List<Flowerdto> allFlower = flowerService.listAll();
        if(allFlower.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allFlower);
    }
        @Operation(summary = "Updates a flower using the new flower data", description = "Updates the data of the selected flower")
        @ApiResponse(responseCode = "201", description = "Flower updated", content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = Flowerdto.class))})
        @ApiResponse(responseCode = "404", description = "Flower not found", content = @Content)
    @PutMapping(value = "/update/{id}")
    @ApiResponse(responseCode = "406", description = "Flower values not valid", content = @Content)
    public ResponseEntity<?> update(@Valid @RequestBody Flowerdto flowerdto, BindingResult result, @PathVariable int id){
        LOG.info("Using method: updateFlower " + flowerdto);
        try{
            Flowerdto flowerDBdto = flowerService.update(flowerdto, id, result);
            flowerDBdto = flowerService.update(flowerdto, id, result);
            if(result.hasErrors()){
                Map<String, String> error = new HashMap<>();
                List<FieldError> errores = result.getFieldErrors();
                for(int i = 0; i < errores.size(); i++){
                    String campo = errores.get(i).getField();
                    String mensaje = errores.get(i).getDefaultMessage();
                    error.put(errores.size() - i + ") Field: " + campo, "Message: " + mensaje);
                    LOG.info("field {}",campo + "message {}", mensaje);
                }
                return new ResponseEntity<Map<String,String>>(error, HttpStatus.NOT_ACCEPTABLE);
            }else {
                return ResponseEntity.ok(flowerDBdto);
            }
        } catch (ResponseStatusException e){
            Map<String, Object> error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Reason", e.getReason());
            return new ResponseEntity<Map<String,Object>>(error, HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Deletes a flower", description = "Deletes a flower using the id as a key")
    @ApiResponse(responseCode = "200", description = "Flower DELETED", content = @Content)
    @ApiResponse(responseCode = "404", description = "Flower not found", content = @Content)
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        LOG.info("Using method delete");
        Flowerdto flowerDelete = null;
        try {
            flowerDelete = flowerService.findById(id);
            flowerService.delete(flowerDelete.getId());
            return ResponseEntity.ok(flowerDelete);

        } catch (ResponseStatusException e){
        Map<String, Object> error = new HashMap<>();
        error.put("Message", e.getMessage());
        error.put("Reason", e.getReason());
        return new ResponseEntity<Map<String,Object>>(error, HttpStatus.NOT_FOUND);
        }

    }
}
