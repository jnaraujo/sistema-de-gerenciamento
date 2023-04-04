package com.uefs.sistemadegerenciamento.dao.inventory;

import com.uefs.sistemadegerenciamento.model.ComputerComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryInventoryDao implements InventoryDao {

    private HashMap<String, ComputerComponent> components;

    public InMemoryInventoryDao() {
        components = new HashMap<>();
    }

    @Override
    public void save(ComputerComponent component) {
        components.put(component.getId(), component);
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
}
