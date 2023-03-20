package cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.controller;


/*
http://localhost:9001/flor/add
http://localhost:9001/flor/update
http://localhost:9001/flor/delete/{id}
http://localhost:9001/flor/getOne/{id}
http://localhost:9001/flor/getAll
 */

import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.domain.Flower;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.dto.Flowerdto;
import cat.itacademy.barcelonactiva.rucker.dario.s05.t01.n02.service.IFlowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flower")
public class FlowerController {

    private static Logger LOG = LoggerFactory.getLogger(FlowerController.class);
    @Autowired
    private IFlowerService flowerService;

    @PostMapping(value = "/add")
    public ResponseEntity<Flowerdto> create(@RequestBody Flowerdto flowerdto){
        LOG.info("Using method createFruit");
        return  ResponseEntity.status(HttpStatus.CREATED).body(flowerService.save(flowerdto));
    }

}
