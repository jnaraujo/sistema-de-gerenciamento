package com.uefs.sistemadegerenciamento.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void testCustomer() {
        Customer customer = new Customer(
                "1",
                "John Doe",
                "123 Main St",
                "555-555-5555",
                "test@test.com");
        assertEquals("1", customer.getId());
        assertEquals("John Doe", customer.getName());
        assertEquals("123 Main St", customer.getAddress());
        assertEquals("555-555-5555", customer.getPhone());
        assertEquals("test@test.com", customer.getEmail());
    }

    @Test
    void testSetters() {
        Customer customer = new Customer(
                "1",
                "José da Silva",
                "Rua A, 123",
                "555-555-5555",
                "test@test.com"
        );
        customer.setName("João Antônio");
        customer.setAddress("Rua Ferraz, 456");
        customer.setPhone("123-456-7890");
        customer.setEmail("joao@test.com");
        assertEquals("1", customer.getId());
        assertEquals("João Antônio", customer.getName());
        assertEquals("Rua Ferraz, 456", customer.getAddress());
        assertEquals("123-456-7890", customer.getPhone());
        assertEquals("joao@test.com", customer.getEmail());
    }
}