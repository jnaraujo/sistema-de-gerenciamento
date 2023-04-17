package com.uefs.sistemadegerenciamento.model.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Classe que implementa {@link Service} e representa um serviço de limpeza.
 * </p>
 * <p>
 *     Um serviço de limpeza é composto por uma lista de componentes, um preço e um custo.
 * </p>
 *
 * @see Service
 * @see com.uefs.sistemadegerenciamento.model.WorkOrder
 */
public class CleaningService implements Service {
    private final List<String> components;
    private String id;
    private double price;
    private double cost;

    /**
     * Criar um serviço de limpeza
     * @param price preço do serviço
     * @param cost custo do serviço
     */
    public CleaningService(double price, double cost) {
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
     * Retorna o preço do serviço
     * @return o preço do serviço
     */
    @Override
    public double getPrice() {
        return this.price;
    }

    /**
     * Define o preço serviço de limpeza
     * @param price Preço do serviço de limpeza
     */
    public void setPrice(Double price){
        this.price = price;
    }

    /**
     * Retorna o custo do serviço
     * @return o custo do serviço
     */
    @Override
    public double getCost() {
        return this.cost;
    }

    /**
     * Define o custo do serviço de limpeza
     * @param cost Custo do serviço
     */
    public void setCost(Double cost){
        this.cost = cost;
    }

    /**
     * Retorna o id do serviço
     * @return o id do serviço
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

    @Override
    public boolean equals(Object object){
        if(!(object instanceof CleaningService)) return false;

        CleaningService service = (CleaningService) object;

        if (this.id == null || service.getId() == null) return false;
        
        return service.getId().equals(this.id);
    }

    @Override
    public String toString() {
        return "CleaningService [id=" + id + ", price=" + price + ", cost=" + cost + "]";
    }
}
