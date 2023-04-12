package com.uefs.sistemadegerenciamento.dao.installation_service;

import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryInstallationServiceDao implements InstallationServiceDao {

    HashMap<String, InstallationService> services;

    public InMemoryInstallationServiceDao() {
        services = new HashMap<>();
    }

    @Override
    public InstallationService save(InstallationService installationService) {
        String id = IdGenerator.generate();
        installationService.setId(id);

        services.put(installationService.getId(), installationService);

        return installationService;
    }

    @Override
    public void delete(String id) {
        services.remove(id);
    }

    @Override
    public void update(InstallationService installationService) {
        services.put(installationService.getId(), installationService);
    }

    @Override
    public InstallationService findById(String id) {
        return services.get(id);
    }

    @Override
    public List<InstallationService> getAll() {
        return new ArrayList<>(services.values());
    }

    @Override
    public void deleteAll() {
        services.clear();
    }
}
