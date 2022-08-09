package com.example.fishapi.config;

import com.example.fishapi.entity.Fish;
import com.example.fishapi.repository.FishRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class FishConfig {

    @Bean
    CommandLineRunner commandLineRunner(FishRepository fishRepository) {
        return args -> {
            Fish f1 = new Fish(
                    "Salmon",
                    LocalDate.of(2022,8,1),
                    13,
                    6.99
            );
            Fish f2 = new Fish(
                    "Haddock",
                    LocalDate.of(2022,7,30),
                    36,
                    3.50
            );
            Fish f3 = new Fish(
                    "Mackerel",
                    LocalDate.of(2022,8,8),
                    55,
                    3.6
            );
            Fish f4 = new Fish(
                    "Cod",
                    LocalDate.of(2022,7,27),
                    24,
                    4.25
            );
            Fish f5 = new Fish(
                    "Sea bass",
                    LocalDate.of(2022,8,4),
                    43,
                    3.85
            );

            fishRepository.saveAll(List.of(f1, f2, f3, f4, f5));
        };
    }
}
