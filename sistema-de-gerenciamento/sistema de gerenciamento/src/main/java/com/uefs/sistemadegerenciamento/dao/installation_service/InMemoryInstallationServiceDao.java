package com.uefs.sistemadegerenciamento.dao.installation_service;

import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *     Implementação de {@link InstallationServiceDao} que armazena os dados em memória.
 * </p>
 *
 * @see InstallationServiceDao
 * @author Jônatas Araújo
 */
public class InMemoryInstallationServiceDao implements InstallationServiceDao {

    HashMap<String, InstallationService> services;

    /**
     * Cria um novo {@link InMemoryInstallationServiceDao}.
     */
    public InMemoryInstallationServiceDao() {
        services = new HashMap<>();
    }

    /**
     * Salva um service.
     * @param installationService Service a ser salvo.
     * @return Service salvo com o ID gerado.
     */
    @Override
    public InstallationService save(InstallationService installationService) {
        String id = IdGenerator.generate();
        installationService.setId(id);

        services.put(installationService.getId(), installationService);

        return installationService;
    }

    /**
     * Deleta um service pelo ID.
     * @param id ID do service a ser deletado.
     */
    @Override
    public void delete(String id) {
        services.remove(id);
    }

    /**
     * Atualiza um service.
     * @param installationService Service a ser atualizado.
     */
    @Override
    public void update(InstallationService installationService) {
        services.put(installationService.getId(), installationService);
    }

    /**
     * Busca um service pelo ID.
     * @param id ID do service a ser buscado.
     * @return Service com o ID informado.
     */
    @Override
    public InstallationService findById(String id) {
        return services.get(id);
    }

    /**
     * Busca todos os services cadastrados.
     * @return Todos os services cadastrados.
     */
    @Override
    public List<InstallationService> getAll() {
        return new ArrayList<>(services.values());
    }

    /**
     * Deleta todos os services cadastrados.
     */
    @Override
    public void deleteAll() {
        services.clear();
    }
}
