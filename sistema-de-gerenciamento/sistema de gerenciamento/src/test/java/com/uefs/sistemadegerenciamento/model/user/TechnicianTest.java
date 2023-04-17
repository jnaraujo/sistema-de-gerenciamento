package com.uefs.sistemadegerenciamento.model.user;

import com.uefs.sistemadegerenciamento.constants.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechnicianTest {
    @Test
    void testTechnician() {
        Technician technician = new Technician(
                "João da Silva",
                "joao@test.com",
                "123456"
        );
        technician.setId("1");

        assertEquals("1", technician.getId());
        assertEquals("João da Silva", technician.getName());
        assertEquals("joao@test.com", technician.getEmail());

        technician.setName("João da Silva Junior");
        technician.setEmail("jjunior@test.com");

        assertEquals("João da Silva Junior", technician.getName());
        assertEquals("jjunior@test.com", technician.getEmail());

        assertEquals(UserType.TECHNICIAN, technician.getUserType());
    }
}