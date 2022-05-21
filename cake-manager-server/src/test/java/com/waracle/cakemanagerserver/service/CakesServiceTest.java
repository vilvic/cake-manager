package com.waracle.cakemanagerserver.service;

import com.waracle.cakemanagerserver.controller.model.AddCake;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = {"/db/schema.sql", "/db/test-data.sql"})
class CakesServiceTest {

    @Autowired
    private CakesService cakesService;

    @Test
    void GetCakes_CakesExist_RecordsReturned() {
        final var cakes = cakesService.getCakes();
        assertNotNull(cakes);
        assertEquals(3, cakes.size());
    }

    @Test
    void GetCake_CakeAlreadyExists_CakeFound() {
        final var optionalCake = cakesService.getByTitle("Cheesecake");
        assertTrue(optionalCake.isPresent());
    }

    @Test
    void GetCake_CakeExistDoesNotExit_CakeNotFound() {
        final var optionalCake = cakesService.getByTitle("Lemon drizzle");
        assertFalse(optionalCake.isPresent());
    }

    @Test
    void AddCake_ValidCakeData_CakeSaved() {

        final var addCake = new AddCake(
                "Lemon drizzle",
                "A lemon drizzle Cake is an easy tea cake with a distinctive crunchy lemon glaze",
                "http://");

        final var savedCake = cakesService.addCake(addCake);
        assertNotNull(savedCake);
        assertNotNull(savedCake.getId());
        assertEquals("Lemon drizzle", savedCake.getTitle());
        assertEquals("A lemon drizzle Cake is an easy tea cake with a distinctive crunchy lemon glaze", savedCake.getDescription());
        assertEquals("http://", savedCake.getImageUrl());

    }

}
