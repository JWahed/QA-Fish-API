package com.example.fishapi.repository;

import com.example.fishapi.entity.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {

    List<Fish> findFishByName(String name);
    List<Fish> findAllFishByPrice(double price);

    @Query("Select f from Fish f WHERE f.quantity = ?1 and f.price = ?2")
    List<Fish> findAllFishByQuantityAndPrice(Integer quantity, Double price);
}
