package com.uefs.sistemadegerenciamento.model;

/**
 * Classe que representa um componente de computador que não está no estoque
 */
public class OtherComponent implements Component {
    private String description;
    private Double price;
    private Double cost;
    public OtherComponent(String description, Double price, Double cost) {
        this.description = description;
        this.price = price;
        this.cost = cost;
    }

    /**
     * @return Retorna a descrição do componente
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define a descrição do componente
     * @param description Descrição do componente
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Retorna o preço do componente
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Define o preço do componente
     * @param price Preço do componente
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return Retorna o custo do componente
     */
    public Double getCost() {
        return cost;
    }

    /**
     * Define o custo do componente
     * @param cost Custo do componente
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }
}
