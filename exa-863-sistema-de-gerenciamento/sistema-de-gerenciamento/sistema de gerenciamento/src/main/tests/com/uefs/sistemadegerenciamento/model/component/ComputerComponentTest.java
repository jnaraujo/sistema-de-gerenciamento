package com.uefs.sistemadegerenciamento.model.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerComponentTest {

    private ComputerComponent computerComponent;

    @BeforeEach
    void setUp() {
        computerComponent = new ComputerComponent(
                "HYPERX 8GB 2400MHz",
                "HYPERX",
                200.0,
                100.0,
                1
        );
        computerComponent.setId("1");
    }

    @Test
    void testComputerComponentCreation() {
        assertNotNull(this.computerComponent);
        assertEquals("HYPERX 8GB 2400MHz", this.computerComponent.getName());
        assertEquals("HYPERX", this.computerComponent.getManufacturer());
        assertEquals(200.0, this.computerComponent.getPricePerUnit());
        assertEquals(100.0, this.computerComponent.getCostPerUnit());
    }

    @Test
    void testToString() {
        String expected = "ComputerComponent [id=1, name=HYPERX 8GB 2400MHz, manufacturer=HYPERX, pricePerUnit=200.0, costPerUnit=100.0, quantity=1]";
        assertEquals(expected, computerComponent.toString());
    }

}