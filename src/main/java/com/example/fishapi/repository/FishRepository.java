package com.example.fishapi.repository;

import com.example.fishapi.entity.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {

}
