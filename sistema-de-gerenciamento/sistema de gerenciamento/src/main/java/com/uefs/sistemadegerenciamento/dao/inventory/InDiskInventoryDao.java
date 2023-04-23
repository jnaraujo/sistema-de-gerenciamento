package com.uefs.sistemadegerenciamento.dao.inventory;

import com.uefs.sistemadegerenciamento.dao.FileManager;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *     Implementação de {@link InventoryDao} que armazena os dados em disco.
 * </p>
 *
 * @see InventoryDao
 * @author Jônatas Araújo
 */
public class InDiskInventoryDao implements InventoryDao {

    private HashMap<String, ComputerComponent> components;
    private FileManager<String, ComputerComponent> fileManager;

    /**
     * Cria um novo {@link InDiskInventoryDao}.
     */
    public InDiskInventoryDao(String fileName) {
        fileManager = new FileManager(fileName);
        components = fileManager.load();
    }

    /**
     * Salva um componente.
     * @param component Componente a ser salvo.
     * @return Componente salvo com o ID gerado.
     */
    @Override
    public ComputerComponent save(ComputerComponent component) {
        String id = IdGenerator.generate();
        component.setId(id);

        components.put(component.getId(), component);

        fileManager.save(components);
        return component;
    }

    /**
     * Deleta um componente pelo ID.
     * @param id ID do componente a ser deletado.
     */
    @Override
    public void delete(String id) {
        components.remove(id);
        fileManager.save(components);
    }

    /**
     * Atualiza um componente.
     * @param component Componente a ser atualizado.
     */
    @Override
    public void update(ComputerComponent component) {
        components.put(component.getId(), component);
        fileManager.save(components);
    }

    /**
     * Busca um componente pelo ID.
     * @param id ID do componente a ser buscado.
     * @return Componente com o ID informado.
     */
    @Override
    public ComputerComponent findById(String id) {
        return components.get(id);
    }

    /**
     * Busca um componente pelo nome.
     * @return Todos os componentes.
     */
    @Override
    public List<ComputerComponent> getAll() {
        return new ArrayList<>(components.values());
    }

    /**
     * Deleta todos os componentes.
     */
    @Override
    public void deleteAll() {
        components.clear();
        fileManager.save(components);
    }

    /**
     * Retorna a média de preço dos componentes.
     * @return A média de preço dos componentes.
     */
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

    /**
     * Retorna a média de custo dos componentes.
     * @return A média de custo dos componentes.
     */
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

    /**
     * <p>
     *     Retorna todos os componentes disponíveis.
     * </p>
     * <p>
     *     Um componente está disponível se sua quantidade for maior que 0.
     * </p>
     * @return Todos os componentes disponíveis.
     */
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
