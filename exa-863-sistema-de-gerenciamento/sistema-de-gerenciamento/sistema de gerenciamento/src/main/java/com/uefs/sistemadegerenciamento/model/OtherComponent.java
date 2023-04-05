package com.uefs.sistemadegerenciamento.model;

/**
 * Classe que representa um componente de computador que não está no estoque
 */
public class OtherComponent implements Component {
    private String description;
    private Double pricePerUnit;
    private Double costPerUnit;
    private Integer quantity;

    public OtherComponent(String description, Double pricePerUnit, Double costPerUnit, Integer quantity) {
        this.description = description;
        this.pricePerUnit = pricePerUnit;
        this.costPerUnit = costPerUnit;
        this.quantity = quantity;
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
    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    /**
     * Define o preço do componente
     * @param price Preço do componente
     */
    public void setPricePerUnit(Double price) {
        this.pricePerUnit = price;
    }

    /**
     * @return Retorna o custo do componente
     */
    public Double getCostPerUnit() {
        return costPerUnit;
    }

    /**
     * @return Retorna a quantidade do componente
     */
    @Override
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Define o custo do componente
     * @param cost Custo do componente
     */
    public void setCostPerUnit(Double cost) {
        this.costPerUnit = cost;
    }
}
