package com.uefs.sistemadegerenciamento.model.user;

import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

        @Test
        void testUser() {
            User user = new Technician(
                    "1",
                    "João da Silva",
                    "joao@test.com",
                    "123456"
            );

            assertEquals("1", user.getId());
            assertEquals("João da Silva", user.getName());
            assertEquals("joao@test.com", user.getEmail());
        }

}