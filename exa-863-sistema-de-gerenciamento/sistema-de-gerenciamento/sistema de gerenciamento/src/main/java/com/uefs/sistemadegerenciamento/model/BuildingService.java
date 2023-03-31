package com.uefs.sistemadegerenciamento.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um serviço de montagem de computador
 */
public class BuildingService implements Service{
    private List<Component> usedComponents;
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
     * @return Retorna o custo do serviço
     */
    @Override
    public double getCost() {
        double cost = 0;
        for (Component component : usedComponents) {
            cost += component.getCost();
        }
        return cost;
    }

    /**
     * Retorna o preço do serviço
     * @return Retorna o preço do serviço
     */
    @Override
    public double getPrice() {
        double price = 0;
        for (Component component : usedComponents) {
            price += component.getPrice();
        }
        return price;
    }
}
