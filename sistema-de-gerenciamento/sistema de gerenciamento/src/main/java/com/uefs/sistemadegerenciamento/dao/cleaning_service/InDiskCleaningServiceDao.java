package com.uefs.sistemadegerenciamento.dao.cleaning_service;

import com.uefs.sistemadegerenciamento.dao.FileManager;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *     Implementação de {@link CleaningServiceDao} que armazena os dados em disco.
 * </p>
 *
 * @see CleaningServiceDao
 * @author Jônatas Araújo
 */
public class InDiskCleaningServiceDao implements CleaningServiceDao{

    private HashMap<String, CleaningService> cleaningServices;
    private FileManager<String, CleaningService> fileManager;

    /**
     * Cria um novo objeto {@link InDiskCleaningServiceDao}
     */
    public InDiskCleaningServiceDao(String fileName) {
        fileManager = new FileManager<>(fileName);
        cleaningServices = fileManager.load();
    }

    /**
     * Salva um serviço de limpeza no banco de dados
     * @param cleaningService o serviço de limpeza a ser salvo
     * @return o serviço de limpeza salvo
     */
    @Override
    public CleaningService save(CleaningService cleaningService) {
        String id = IdGenerator.generate();
        cleaningService.setId(id);

        cleaningServices.put(cleaningService.getId(), cleaningService);

        fileManager.save(cleaningServices);
        return cleaningService;
    }

    /**
     * Deleta um serviço de limpeza do banco de dados
     * @param id o id do serviço de limpeza a ser deletado
     */
    @Override
    public void delete(String id) {
        cleaningServices.remove(id);
        fileManager.save(cleaningServices);
    }

    /**
     * Atualiza um serviço de limpeza no banco de dados
     * @param cleaningService o serviço de limpeza a ser atualizado
     */
    @Override
    public void update(CleaningService cleaningService) {
        cleaningServices.put(cleaningService.getId(), cleaningService);
        fileManager.save(cleaningServices);
    }

    /**
     * Busca um serviço de limpeza no banco de dados
     * @param id o id do serviço de limpeza a ser buscado
     * @return o serviço de limpeza com o id passado como parâmetro
     */
    @Override
    public CleaningService findById(String id) {
        return cleaningServices.get(id);
    }

    /**
     * Retorna todos os serviços de limpeza do banco de dados
     * @return todos os serviços de limpeza do banco de dados
     */
    @Override
    public List<CleaningService> getAll() {
        return new ArrayList<>(cleaningServices.values());
    }

    /**
     * Deleta todos os serviços de limpeza do banco de dados
     */
    @Override
    public void deleteAll() {
        cleaningServices.clear();
        fileManager.save(cleaningServices);
    }
}
