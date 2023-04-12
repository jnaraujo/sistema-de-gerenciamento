package com.uefs.sistemadegerenciamento.dao.cleaning_service;

import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryCleaningServiceDao implements CleaningServiceDao{

    HashMap<String, CleaningService> cleaningServices;

    public InMemoryCleaningServiceDao() {
        cleaningServices = new HashMap<>();
    }

    @Override
    public CleaningService save(CleaningService cleaningService) {
        String id = IdGenerator.generate();
        cleaningService.setId(id);

        cleaningServices.put(cleaningService.getId(), cleaningService);

        return cleaningService;
    }

    @Override
    public void delete(String id) {
        cleaningServices.remove(id);
    }

    @Override
    public void update(CleaningService cleaningService) {
        cleaningServices.put(cleaningService.getId(), cleaningService);
    }

    @Override
    public CleaningService findById(String id) {
        return cleaningServices.get(id);
    }

    @Override
    public List<CleaningService> getAll() {
        return new ArrayList<>(cleaningServices.values());
    }

    @Override
    public void deleteAll() {
        cleaningServices.clear();
    }
}
