package com.example.fishapi.controller;

import com.example.fishapi.entity.Fish;
import com.example.fishapi.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Fish> getFishes() {
        return fishService.getAllFishes();
    }

    @GetMapping("/get/{id}")
    public Optional<Fish> getFish(@PathVariable Long id) {
        return fishService.getFishById(id);
    }

    @PostMapping("/post")
    public void createFish(@RequestBody Fish fish) {
        fishService.addFish(fish);
    }

    @PatchMapping("/patch/{id}")
    public void updateFish(@PathVariable Long id,
                           @PathParam("name") String name,
                           @PathParam("dateCaught") String dateCaught,
                           @PathParam("quantity") Integer quantity,
                           @PathParam("price") Double price)
                            throws NullPointerException {

        fishService.updateFish(id, name, dateCaught, quantity, price);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFish(@PathVariable Long id)
                            throws NullPointerException {

        fishService.removeFish(id);
    }
}
