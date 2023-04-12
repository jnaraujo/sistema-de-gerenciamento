package com.uefs.sistemadegerenciamento.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

        @Test
        void testUser() {
            User user = new Technician(
                    "João da Silva",
                    "joao@test.com",
                    "123456"
            );
            user.setId("1");

            assertEquals("1", user.getId());
            assertEquals("João da Silva", user.getName());
            assertEquals("joao@test.com", user.getEmail());
            assertEquals("User [id=1, name=João da Silva, email=joao@test.com, password=123456, userType=TECHNICIAN]", user.toString());
        }

        @Test
        void testEquals(){
            User technician = new Technician(
                    "João",
                    "joao@test.com",
                    "4565"
            );
            technician.setId("1");

            User administrator = new Administrator(
                    "Pedro",
                    "pedro@test.com",
                    "123"
            );
            administrator.setId("2");

            User receptionist = new Receptionist(
                    "Pedro",
                    "pedro@test.com",
                    "abc"
            );
            receptionist.setId("2");

            assertNotEquals(technician, administrator);
            assertNotEquals(technician, receptionist);
            assertEquals(administrator, receptionist);
        }

}