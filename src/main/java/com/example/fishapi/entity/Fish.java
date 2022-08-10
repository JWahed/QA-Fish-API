package com.example.fishapi.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Fish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateCaught;
    private int quantity;
    private double price;

    public Fish(String name, LocalDate dateCaught, int quantity, double price) {
        this.name = name;
        this.dateCaught = dateCaught;
        this.quantity = quantity;
        this.price = price;
    }
}
