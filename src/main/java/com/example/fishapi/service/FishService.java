package com.example.fishapi.service;

import com.example.fishapi.entity.Fish;
import com.example.fishapi.repository.FishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FishService {

    private final FishRepository fishRepository;

    @Autowired
    public FishService(FishRepository fishRepository) {
        this.fishRepository = fishRepository;
    }

    public List<Fish> getAllFishes() {
        return fishRepository.findAll();
    }

    public List<Fish> getFishByName(String name) {
        return fishRepository.findFishByName(name);
    }

    public Optional<Fish> getFishById(Long id) {
        return fishRepository.findById(id);
    }

    public Fish addFish(Fish fish) {
        fishRepository.save(fish);
        return fish;
    }


    public Optional<Fish> updateFish(Long id,
                                     String name,
                                     String dateCaught,
                                     Integer quantity,
                                     Double price) {

        Optional<Fish> fish = fishRepository.findById(id);
        fish.ifPresent(f -> {
            if (name != null && !name.isBlank()) {
                f.setName(name);
            }
            if (dateCaught != null && !dateCaught.isBlank()) {
                f.setDateCaught(LocalDate.parse(dateCaught));
            }
            if (quantity != null && quantity > 0) {
                f.setQuantity(quantity);
            }
            if (price != null && price > 0) {
                f.setPrice(price);
            }
            fishRepository.save(f);
        });
        return fish;
    }

    public Boolean removeFish(Long id) {

        Boolean b = fishRepository.existsById(id);

        if (b) {
            fishRepository.deleteById(id);
        }

        return b;
    }
}
