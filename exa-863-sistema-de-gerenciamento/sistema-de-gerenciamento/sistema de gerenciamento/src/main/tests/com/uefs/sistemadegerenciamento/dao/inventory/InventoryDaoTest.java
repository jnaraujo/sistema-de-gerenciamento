package com.uefs.sistemadegerenciamento.dao.inventory;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InventoryDaoTest {
    private InventoryDao inventoryDao;
    private ComputerComponent component;

    @BeforeEach
    void setUp() {
        inventoryDao = DAOManager.getInventoryDao();
        component = new ComputerComponent(
                "Placa de Vídeo",
                "Placa de vídeo de 8GB",
                1500.00,
                750.00,
                1
        );
    }

    @AfterEach
    void tearDown() {
        inventoryDao.deleteAll();
    }

    @Test
    void testSave() {
        component = inventoryDao.save(component);
        assertEquals(component, inventoryDao.findById(component.getId()));
    }

    @Test
    void testDelete() {
        component = inventoryDao.save(component);
        assertNotNull(inventoryDao.findById(component.getId()));
        inventoryDao.delete(component.getId());
        assertNull(inventoryDao.findById(component.getId()));
    }

    @Test
    void testUpdate() {
        component = inventoryDao.save(component);
        assertEquals(750.00, inventoryDao.findById(component.getId()).getCostPerUnit());

        component.setCostPerUnit(500.00);
        inventoryDao.update(component);

        assertEquals(500.00, inventoryDao.findById(component.getId()).getCostPerUnit());
    }

    @Test
    void testFindById() {
        component = inventoryDao.save(component);
        assertEquals(component, inventoryDao.findById(component.getId()));
    }

    @Test
    void testGetAll() {
        component = inventoryDao.save(component);
        inventoryDao.save(new ComputerComponent(
                "Placa Mãe",
                "Placa mãe de 8GB",
                1500.00,
                750.00,
                2
        ));
        assertEquals(2, inventoryDao.getAll().size());
    }

    @Test
    void testGetAveragePrice() {
        component = inventoryDao.save(component); // 1500.00
        inventoryDao.save(new ComputerComponent(
                "Placa Mãe",
                "Placa mãe de 8GB",
                1500.00,
                750.00,
                2
        )); // 3000.00
        assertEquals(1500.00, inventoryDao.getAveragePrice());
    }

    @Test
    void testGetAverageCost() {
        component = inventoryDao.save(component); // 750.00
        inventoryDao.save(new ComputerComponent(
                "Placa Mãe",
                "Placa mãe de 8GB",
                1500.00,
                750.00,
                2
        ));
        double average = (1500.00 + 750.00) / 3;
        assertEquals(average, inventoryDao.getAverageCost());
    }

    @Test
    void testFindAvailableComponents() {
        component = inventoryDao.save(component);
        inventoryDao.save(new ComputerComponent(
                "Placa Mãe",
                "Placa mãe de 8GB",
                1500.00,
                750.00,
                0
        ));
        assertEquals(component.getId(), inventoryDao.findAvailableComponents().get(0).getId());
    }
}