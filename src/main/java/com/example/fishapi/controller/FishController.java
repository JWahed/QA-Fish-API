package com.example.fishapi.controller;

import com.example.fishapi.entity.Fish;
import com.example.fishapi.service.FishService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;


@RequestMapping("/api/v1/fish")
@RestController
public class FishController {

    private final FishService fishService;

    @Autowired
    public FishController(FishService fishService) {

        this.fishService = fishService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List> getFishes() {

        return new ResponseEntity<List>(fishService.getAllFishes(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Fish> getFish(@PathVariable Long id) {

        Optional<Fish> f = fishService.getFishById(id);
        return f.isPresent() ? ResponseEntity.ok(f.get()) : ResponseEntity.of(f);
    }

    @PostMapping("/post")
    public ResponseEntity<Fish> createFish(@RequestBody Fish fish) {

        if (fish == null) {
            return new ResponseEntity<Fish>(HttpStatus.BAD_REQUEST);
        }
        fishService.addFish(fish);
        return new ResponseEntity<Fish>(fish, HttpStatus.CREATED);
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<Fish> updateFish(@PathVariable Long id,
                                           @PathParam("name") String name,
                                           @PathParam("dateCaught") String dateCaught,
                                           @PathParam("quantity") Integer quantity,
                                           @PathParam("price") Double price) {

        if (name == null && name.isBlank() &&
            dateCaught == null && dateCaught.isBlank() &&
            quantity == null && quantity > 0 &&
            price == null && price > 0) {

            return new ResponseEntity<Fish>(HttpStatus.NO_CONTENT);
        }

        Fish f = fishService.updateFish(id, name, dateCaught, quantity, price);

        return new ResponseEntity<Fish>(f, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteFish(@PathVariable Long id) {

        fishService.removeFish(id);
        Optional<Fish> f = fishService.getFishById(id);
        return f.isPresent() ?
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
