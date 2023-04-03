package com.uefs.sistemadegerenciamento.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void testInventory() {
        ComputerComponent component = new ComputerComponent(
                "1",
                "processador i5 10th gen",
                "amd",
                1000.0,
                500.0
        );
        inventory.addComponent(component);
        assertTrue(inventory.getComponents().contains(component));
    }

    @Test
    void testRemoveComponent() {
        inventory.addComponent(new ComputerComponent(
                "1",
                "placa de video",
                "amd",
                1000.0,
                500.0
        ));

        inventory.addComponent(new ComputerComponent(
                "2",
                "placa mãe",
                "giagabyte",
                1500.0,
                700.0
        ));

        assertEquals("placa de video", inventory.getComponents().get(0).getName());
        assertEquals("placa mãe", inventory.getComponents().get(1).getName());

        inventory.removeComponent(new ComputerComponent(
                "1",
                "placa de video",
                "amd",
                1000.0,
                500.0
        ));

        assertEquals("placa mãe", inventory.getComponents().get(0).getName());
    }


}