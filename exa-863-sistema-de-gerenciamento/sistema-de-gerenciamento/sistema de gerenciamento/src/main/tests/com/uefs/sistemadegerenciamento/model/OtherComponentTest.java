package com.uefs.sistemadegerenciamento.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtherComponentTest {

    private OtherComponent otherComponent;

    @BeforeEach
    void setUp() {
        this.otherComponent = new OtherComponent("other component", 100.0, 50.0);
    }

    @Test
    void testOtherComponentCreation() {
        assertNotNull(this.otherComponent);
        assertEquals("other component", this.otherComponent.getDescription());
        assertEquals(100.0, this.otherComponent.getPrice());
        assertEquals(50.0, this.otherComponent.getCost());
    }

}