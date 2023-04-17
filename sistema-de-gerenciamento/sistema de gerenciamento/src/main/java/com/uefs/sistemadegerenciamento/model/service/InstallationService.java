package com.uefs.sistemadegerenciamento.model.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Classe que implementa {@link Service} e representa um serviço de instalação.
 * </p>
 * <p>
 *     Um serviço de instalação é composto por uma lista de componentes, um preço e um custo.
 * </p>
 *
 * @see Service
 * @see com.uefs.sistemadegerenciamento.model.WorkOrder
 * @author Jônatas Araújo
 */
public class InstallationService implements Service {
    private String id;
    private double price;
    private double cost;
    private String description;

    /**
     * Cria um serviço de instalação
     * @param description Descrição do serviço
     * @param price Preço do serviço
     * @param cost Custo do serviço
     */
    public InstallationService(String description, double price, double cost) {
        this.price = price;
        this.cost = cost;
        this.description = description;
    }

    /**
     * Retorna a descrição do serviço
     * @return a descrição do serviço
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Define a descrição do serviço
     * @param description Descrição do serviço
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Define o preço do serviço
     * @param price Preço do serviço
     */
    public void setPrice(double price) {
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
     * Define o custo do serviço
     * @param cost Custo do serviço
     */
    public void setCost(double cost) {
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
     * Define o id do serviço
     * @param id Id do serviço
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "InstallationService [id=" + id + ", cost=" + cost + ", price=" + price + ", description=" + description + "]";
    }
}
