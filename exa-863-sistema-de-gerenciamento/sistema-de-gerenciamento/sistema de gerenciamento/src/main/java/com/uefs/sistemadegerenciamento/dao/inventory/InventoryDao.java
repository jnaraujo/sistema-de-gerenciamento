package com.uefs.sistemadegerenciamento.dao.inventory;

import com.uefs.sistemadegerenciamento.dao.Dao;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;

import java.util.List;

public interface InventoryDao extends Dao<ComputerComponent> {
    double getAveragePrice();
    double getAverageCost();
    List<ComputerComponent> findAvailableComponents();
}
