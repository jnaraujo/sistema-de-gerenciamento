package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.constants.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorTest {
    @Test
    void testReceptionist() {
        Administrator administrator = new Administrator(
                "1",
                "João da Silva",
                "test@test.com",
                "123456"
        );

        assertEquals("1", administrator.getId());
        assertEquals("João da Silva", administrator.getName());
        assertEquals("test@test.com", administrator.getEmail());
        assertEquals("123456", administrator.getPassword());

        administrator.setName("João da Silva Junior");
        administrator.setEmail("joao@test.com");

        assertEquals("João da Silva Junior", administrator.getName());
        assertEquals("joao@test.com", administrator.getEmail());

        assertEquals(UserType.ADMINISTRATOR, administrator.getUserType());
    }
}