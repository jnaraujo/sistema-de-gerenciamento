package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechnicianTest {
    @Test
    void testTechnician() {
        Technician technician = new Technician(
                "1",
                "João da Silva",
                "joao@test.com",
                "123456"
        );

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