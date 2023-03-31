package com.uefs.sistemadegerenciamento.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechnicianTest {
    @Test
    void testTechnician() {
        Technician technician = new Technician(
                "1",
                "João da Silva",
                "joao@test.com"
        );

        assertEquals("1", technician.getId());
        assertEquals("João da Silva", technician.getName());
        assertEquals("joao@test.com", technician.getEmail());

        technician.setName("João da Silva Junior");
        technician.setEmail("jjunior@test.com");

        assertEquals("João da Silva Junior", technician.getName());
        assertEquals("jjunior@test.com", technician.getEmail());
    }
}