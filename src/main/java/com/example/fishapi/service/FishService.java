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
    public Optional<Fish> getFishById(Long id) {
        return fishRepository.findById(id);
    }

    public void addFish(Fish fish) {
        fishRepository.save(fish);
    }

    private Fish getFish(Long id) {
        Fish f = fishRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NullPointerException();
                });
        return f;
    }

    public void updateFish(Long id,
                           String name,
                           String dateCaught,
                           Integer quantity,
                           Double price) {

        try {
            Fish f = getFish(id);

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

        } catch (NullPointerException e) {
            System.out.println("Fish with id " + id + " not found");
        }
    }

    public void removeFish(Long id) {

        try {
            Fish f = getFish(id);

            fishRepository.deleteById(id);

        } catch (NullPointerException e) {
            System.out.println("Fish with id " + id + " not found");
        }
    }
}
