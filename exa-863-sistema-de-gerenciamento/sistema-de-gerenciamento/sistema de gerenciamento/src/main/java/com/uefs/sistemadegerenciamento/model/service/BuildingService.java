package com.uefs.sistemadegerenciamento.model.service;

import com.uefs.sistemadegerenciamento.model.component.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que implementa {@link Service} e representa um serviço de montagem de computador.
 * Um serviço de montagem de computador é composto por uma lista de componentes
 *
 * @see com.uefs.sistemadegerenciamento.model.WorkOrder
 * @author Jônatas Araújo
 */
public class BuildingService implements Service {
    private List<Component> usedComponents;

    /**
     * Cria um serviço de montagem de computador
     */
    public BuildingService() {
        this.usedComponents = new ArrayList<>();
    }

    /**
     * Retorna a lista de componentes usados no serviço
     * @return Lista de componentes usados no serviço
     */
    public List<Component> getUsedComponents() {
        return usedComponents;
    }

    /**
     * Adiciona um componente ao serviço
     * @param component Componente a ser adicionado ao serviço
     */
    public void addComponent(Component component) {
        this.usedComponents.add(component);
    }

    /**
     * Retorna o custo do serviço
     * @return o custo do serviço
     */
    @Override
    public double getCost() {
        double cost = 0;
        for (Component component : usedComponents) {
            cost += component.getCostPerUnit() * component.getQuantity();
        }
        return cost;
    }

    /**
     * Retorna o preço do serviço
     * @return o preço do serviço
     */
    @Override
    public double getPrice() {
        double price = 0;
        for (Component component : usedComponents) {
            price += component.getPricePerUnit() * component.getQuantity();
        }
        return price;
    }

    @Override
    public String toString() {
        return "BuildingService [price=" + getPrice() + ", cost=" + getCost() + "]";
    }
}
