package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CleaningServiceTest {
    @Test
    void testCleaning() {
        double price = 50.0;
        double cost = 25.0;

        CleaningService cleaningService = new CleaningService(IdGenerator.generate(), price, cost);
        cleaningService.addComponent("HD");
        cleaningService.addComponent("Placa Mãe");
        cleaningService.addComponent("Placa de Vídeo");

        assertEquals(cost, cleaningService.getCost());
        assertEquals(price, cleaningService.getPrice());
        assertEquals(3, cleaningService.getComponents().size());
        assertEquals("HD", cleaningService.getComponents().get(0));
        assertEquals("Placa Mãe", cleaningService.getComponents().get(1));
        assertEquals("Placa de Vídeo", cleaningService.getComponents().get(2));
    }
}