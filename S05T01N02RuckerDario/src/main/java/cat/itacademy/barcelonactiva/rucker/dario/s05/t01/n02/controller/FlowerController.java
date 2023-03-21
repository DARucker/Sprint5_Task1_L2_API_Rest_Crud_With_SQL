package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.controller;


/*
http://localhost:9001/flor/add *** DONE
http://localhost:9001/flor/update/{id} *** DONE
http://localhost:9001/flor/delete/{id}
http://localhost:9001/flor/getOne/{id} *** DONE
http://localhost:9001/flor/getAll *** DONE
 */

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.IFlowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flower")
public class FlowerController {

    private static Logger LOG = LoggerFactory.getLogger(FlowerController.class);
    @Autowired
    private IFlowerService flowerService;

    @PostMapping(value = "/add")
    public ResponseEntity<Flowerdto> create(@RequestBody Flowerdto flowerdto){
        LOG.info("Using method: createFlower " + flowerdto);
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
    public ResponseEntity<Flowerdto> update(@RequestBody Flowerdto flowerdto, @PathVariable int id){
        LOG.info("Using method: updateFlower " + flowerdto);

        Flowerdto flowerdtoDB = flowerService.findById(id);
        if(flowerdtoDB == null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.status(HttpStatus.OK).body(flowerService.update(flowerdto));


    }


}
