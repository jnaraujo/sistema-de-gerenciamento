package com.uefs.sistemadegerenciamento.dao.inventory;

import com.uefs.sistemadegerenciamento.model.ComputerComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryInventoryDaoTest {
    private InMemoryInventoryDao inventoryDao;
    private ComputerComponent component;

    @BeforeEach
    void setUp() {
        inventoryDao = new InMemoryInventoryDao();
        component = new ComputerComponent(
                UUID.randomUUID().toString(),
                "Placa de Vídeo",
                "Placa de vídeo de 8GB",
                1500.00,
                750.00
        );
    }

    @Test
    void testSave() {
        inventoryDao.save(component);
        assertEquals(component, inventoryDao.findById(component.getId()));
    }

    @Test
    void testDelete() {
        inventoryDao.save(component);
        assertNotNull(inventoryDao.findById(component.getId()));
        inventoryDao.delete(component.getId());
        assertNull(inventoryDao.findById(component.getId()));
    }

    @Test
    void testUpdate() {
        inventoryDao.save(component);
        assertEquals(750.00, inventoryDao.findById(component.getId()).getCost());

        component.setCost(500.00);
        inventoryDao.update(component);

        assertEquals(500.00, inventoryDao.findById(component.getId()).getCost());
    }

    @Test
    void testFindById() {
        inventoryDao.save(component);
        assertEquals(component, inventoryDao.findById(component.getId()));
    }

    @Test
    void testGetAll() {
        inventoryDao.save(component);
        inventoryDao.save(new ComputerComponent(
                UUID.randomUUID().toString(),
                "Placa Mãe",
                "Placa mãe de 8GB",
                1500.00,
                750.00
        ));
        assertEquals(2, inventoryDao.getAll().size());
    }
}