package com.uefs.sistemadegerenciamento.dao.inventory;

import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jônatas Araújo
 */
public class InMemoryInventoryDao implements InventoryDao {

    private HashMap<String, ComputerComponent> components;

    public InMemoryInventoryDao() {
        components = new HashMap<>();
    }

    @Override
    public ComputerComponent save(ComputerComponent component) {
        String id = IdGenerator.generate();
        component.setId(id);

        components.put(component.getId(), component);

        return component;
    }

    @Override
    public void delete(String id) {
        components.remove(id);
    }

    @Override
    public void update(ComputerComponent component) {
        components.put(component.getId(), component);
    }

    @Override
    public ComputerComponent findById(String id) {
        return components.get(id);
    }

    @Override
    public List<ComputerComponent> getAll() {
        return new ArrayList<>(components.values());
    }

    @Override
    public void deleteAll() {
        components.clear();
    }

    @Override
    public double getAveragePrice() {
        double average = 0;
        int quantity = 0;

        for (ComputerComponent component : components.values()) {
            average += component.getPricePerUnit() * component.getQuantity();
            quantity += component.getQuantity();
        }

        return average / quantity;
    }

    @Override
    public double getAverageCost() {
        double average = 0;
        int quantity = 0;

        for (ComputerComponent component : components.values()) {
            average += component.getCostPerUnit() * component.getQuantity();
            quantity += component.getQuantity();
        }
        return average / quantity;
    }

    @Override
    public List<ComputerComponent> findAvailableComponents() {
        List<ComputerComponent> availableComponents = new ArrayList<>();

        for (ComputerComponent component : components.values()) {
            if (component.getQuantity() > 0) {
                availableComponents.add(component);
            }
        }

        return availableComponents;
    }
}
