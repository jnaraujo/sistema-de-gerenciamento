package com.uefs.sistemadegerenciamento.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerComponentTest {

    private ComputerComponent computerComponent;

    @BeforeEach
    void setUp() {
        this.computerComponent = new ComputerComponent(
                "1",
                "HYPERX 8GB 2400MHz",
                "HYPERX",
                200.0,
                100.0,
                1
        );
    }

    @Test
    void testComputerComponentCreation() {
        assertNotNull(this.computerComponent);
        assertEquals("HYPERX 8GB 2400MHz", this.computerComponent.getName());
        assertEquals("HYPERX", this.computerComponent.getManufacturer());
        assertEquals(200.0, this.computerComponent.getPricePerUnit());
        assertEquals(100.0, this.computerComponent.getCostPerUnit());
    }

}