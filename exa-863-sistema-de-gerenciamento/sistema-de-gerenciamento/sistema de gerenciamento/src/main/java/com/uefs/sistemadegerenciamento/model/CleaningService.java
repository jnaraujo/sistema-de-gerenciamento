package com.uefs.sistemadegerenciamento.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um serviço de limpeza de computadores
 */
public class CleaningService implements Service{
    private final List<String> components;
    private String id;
    private double price;
    private double cost;

    /**
     * @param price preço do serviço
     * @param cost custo do serviço
     */
    public CleaningService(String id, double price, double cost) {
        this.id = id;
        this.components = new ArrayList<>();
        this.price = price;
        this.cost = cost;
    }

    /**
     * Retorna a lista de Componentes usados no serviço
     * @return Lista de Componentes usados no serviço
     */
    public List<String> getComponents() {
        return components;
    }

    /**
     * Adiciona um componente ao serviço
     * @param component Componente a ser adicionado ao serviço
     */
    public void addComponent(String component) {
        this.components.add(component);
    }

    /**
     * @return Retorna o preço do serviço
     */
    @Override
    public double getPrice() {
        return this.price;
    }

    /**
     * @return Retorna o custo do serviço
     */
    @Override
    public double getCost() {
        return this.cost;
    }

    /**
     * @return Retorna o id do serviço
     */
    public String getId() {
        return this.id;
    }

    /**
     * Altera o id do serviço
     * @param id Id do serviço
     */
    public void setId(String id) {
        this.id = id;
    }
}
