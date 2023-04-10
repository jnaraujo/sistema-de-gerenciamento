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
            assertEquals("User [id=1, name=João da Silva, email=joao@test.com, password=123456, userType=TECHNICIAN]", user.toString());
        }

        @Test
        void testEquals(){
            User technician = new Technician(
                    "1",
                    "João",
                    "joao@test.com",
                    "4565"
            );
            User administrator = new Administrator(
                    "2",
                    "Pedro",
                    "pedro@test.com",
                    "123"
            );
            User receptionist = new Receptionist(
                    "2",
                    "Pedro",
                    "pedro@test.com",
                    "abc"
            );

            assertNotEquals(technician, administrator);
            assertNotEquals(technician, receptionist);
            assertEquals(administrator, receptionist);
        }

}