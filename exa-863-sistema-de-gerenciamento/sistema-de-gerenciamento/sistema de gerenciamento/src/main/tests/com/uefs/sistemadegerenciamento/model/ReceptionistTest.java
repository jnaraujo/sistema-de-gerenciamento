package com.uefs.sistemadegerenciamento.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReceptionistTest {
    @Test
    void testReceptionist() {
        Receptionist receptionist = new Receptionist(
                "1",
                "João da Silva",
                "test@test.com"
        );

        assertEquals("1", receptionist.getId());
        assertEquals("João da Silva", receptionist.getName());
        assertEquals("test@test.com", receptionist.getEmail());

        receptionist.setName("João da Silva Junior");
        receptionist.setEmail("joao@test.com");

        assertEquals("João da Silva Junior", receptionist.getName());
        assertEquals("joao@test.com", receptionist.getEmail());
    }
}