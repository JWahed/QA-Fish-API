package com.example.fishapi.mocking.service;

import com.example.fishapi.entity.Fish;
import com.example.fishapi.repository.FishRepository;
import com.example.fishapi.service.FishService;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class FishServiceUnitTest {

    @Autowired
    private FishService fishService;

    @MockBean
    private FishRepository fishRepository;

    @Test
    void testGetAllFishes() {

        List<Fish> expected = List.of(
                new Fish(1L, "salmon", LocalDate.of(2022, 8,1), 13, 6.99),
                new Fish(2L, "haddock", LocalDate.of(2022,7, 30), 36, 3.5));

        Mockito.when(fishRepository.findAll())
                .thenReturn(List.of(
                        new Fish(1L, "salmon", LocalDate.of(2022, 8,1), 13, 6.99),
                        new Fish(2L, "haddock", LocalDate.of(2022,7, 30), 36, 3.5)));

        List<Fish> actual = fishService.getAllFishes();

        assertEquals(expected, actual);
    }

    @Test
    void testGetFishByName() {

        final Long id = 1L;
        final String name = "salmon";
        final LocalDate dateCaught = LocalDate.of(2022, 8,1);
        final Integer quantity = 13;
        final Double price = 6.99;


        List<Fish> expected = List.of(new Fish(id, name, dateCaught, quantity, price));

        Mockito.when(fishRepository.findFishByName(name))
                .thenReturn(List.of(new Fish(id, name, dateCaught, quantity,price)));

        List<Fish> actual = fishService.getFishByName(name);

        assertEquals(expected, actual);

    }

    @Test
    void testGetFishById() {

        final Long id = 1L;
        final String name = "salmon";
        final LocalDate dateCaught = LocalDate.of(2022, 8,1);
        final Integer quantity = 150;
        final Double price = 99.99;

        Optional<Fish> expected = Optional.of(new Fish(id, name, dateCaught, quantity, price));

        Mockito.when(fishRepository.findById(id))
                .thenReturn(Optional.of(new Fish(id, name, dateCaught, quantity, price)));

        Optional<Fish> actual = fishService.getFishById(id);

        assertEquals(expected, actual);
    }

    @Test
    void testAddFish() {

        final String name = "tuna";
        final LocalDate dateCaught = LocalDate.of(2021, 12,29);
        final Integer quantity = 40;
        final Double price = 19.99;

        Fish expected = new Fish(name, dateCaught, quantity, price);

        Mockito.when(fishRepository.save(expected))
                .thenReturn(new Fish(1L, name, dateCaught, quantity, price));

        Fish actual = fishService.addFish(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testUpdateFish() {

        final Long id = 1L;
        final String name = "salmon";
        final LocalDate dateCaught = LocalDate.of(2022, 8,1);
        final Integer newQuantity = 150;
        final Double newPrice = 99.99;

        Optional<Fish> expected = Optional.of(new Fish(id, name, dateCaught, newQuantity, newPrice));

        Mockito.when(fishRepository.findById(id))
                .thenReturn(Optional.of(new Fish(id, name, dateCaught, 49,59.99)));
        Mockito.when(fishRepository.save(new Fish(id, name, dateCaught, newQuantity, newPrice)))
                .thenReturn(new Fish(id, name, dateCaught, newQuantity, newPrice));

        Optional<Fish> actual = fishService.updateFish(id, null, null, newQuantity, newPrice);

        assertEquals(expected, actual);
    }


}
