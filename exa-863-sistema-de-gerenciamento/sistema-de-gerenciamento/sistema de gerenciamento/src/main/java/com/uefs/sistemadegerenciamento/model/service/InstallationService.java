package com.uefs.sistemadegerenciamento.model.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um serviço de instalação de programas e/ou sistemas operacionais
 */
public class InstallationService implements Service {
    private String id;
    private double price;
    private double cost;
    private String description;

    public InstallationService(String id, String description, double price, double cost) {
        this.id = id;
        this.price = price;
        this.cost = cost;
        this.description = description;
    }

    /**
     * @return Retorna a descrição do serviço
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
     * @return Retorna o preço do serviço
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
     * @return Retorna o custo do serviço
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
     * @return Retorna o id do serviço
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
