package com.uefs.sistemadegerenciamento.model.component;

import com.uefs.sistemadegerenciamento.model.component.OtherComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtherComponentTest {

    private OtherComponent otherComponent;

    @BeforeEach
    void setUp() {
        this.otherComponent = new OtherComponent("other component", 100.0, 50.0, 1);
    }

    @Test
    void testOtherComponentCreation() {
        assertNotNull(this.otherComponent);
        assertEquals("other component", this.otherComponent.getName());
        assertEquals(100.0, this.otherComponent.getPricePerUnit());
        assertEquals(50.0, this.otherComponent.getCostPerUnit());
        assertEquals(1, this.otherComponent.getQuantity());
    }

    @Test
    void testToString(){
        String expected = "OtherComponent [costPerUnit=50.0, name=other component, pricePerUnit=100.0, quantity=1]";
        assertEquals(expected, this.otherComponent.toString());
    }

}