package com.uefs.sistemadegerenciamento.dao.technician;

import com.uefs.sistemadegerenciamento.model.Technician;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTechnicianDao implements TechnicianDao {
    private HashMap<String, Technician> technicians = new HashMap<>();

    @Override
    public void save(Technician technician) {
        technicians.put(technician.getId(), technician);
    }

    @Override
    public void delete(String technicianID) {
        technicians.remove(technicianID);
    }

    @Override
    public void update(Technician technician) {
        technicians.replace(technician.getId(), technician);
    }

    @Override
    public Technician findById(String id) {
        return technicians.get(id);
    }

    @Override
    public List<Technician> getAll() {
        return new ArrayList<>(technicians.values());
    }

    @Override
    public void deleteAll() {
        technicians.clear();
    }
}
