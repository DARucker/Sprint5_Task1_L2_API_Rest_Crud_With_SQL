package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.controller;

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.IFlowerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flower")
public class FlowerController {

        private static Logger LOG = LoggerFactory.getLogger(FlowerController.class);
    @Autowired
    private IFlowerService flowerService;

    @PostMapping(value = "/add")
    public ResponseEntity<Flowerdto> create(@Valid @RequestBody Flowerdto flowerdto, BindingResult result){
        LOG.info("Using method: createFlower " + flowerdto);
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(flowerService.create(flowerdto));
    }

    @GetMapping(value = "getOne/{id}")
    public ResponseEntity<Flowerdto> findById (@PathVariable int id){
        LOG.info("Using method getFlower");

        Flowerdto flowerdto = flowerService.findById(id);
        if(null == flowerdto){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flowerdto);
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
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        flowerdto.setId(id);
        Flowerdto flowerDBdto = flowerService.update(flowerdto);
        if(flowerDBdto == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(flowerDBdto);
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
