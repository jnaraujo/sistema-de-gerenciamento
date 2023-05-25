package com.uefs.sistemadegerenciamento.model.component;

import com.uefs.sistemadegerenciamento.model.component.Component;

/**
 * Classe que representa um componente de computador que não está no estoque.
 *
 * @author Jônatas Araújo
 */
public class OtherComponent implements Component {
    private String name;
    private Double pricePerUnit;
    private Double costPerUnit;
    private Integer quantity;

    /**
     * Cria uma nova instância de OtherComponent com os parâmetros especificados.
     * @param name Descrição do componente
     * @param pricePerUnit Preço do componente
     * @param costPerUnit Custo do componente
     * @param quantity Quantidade do componente
     */
    public OtherComponent(String name, Double pricePerUnit, Double costPerUnit, Integer quantity) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.costPerUnit = costPerUnit;
        this.quantity = quantity;
    }

    /**
     * Retorna o nome do componente
     * @return o nome do componente
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Define o nome do componente
     * @param name Nome do componente
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna o preço do componente por unidade
     * @return o preço do componente por unidade
     */
    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    /**
     * Define o preço do componente por unidade
     * @param price Preço do componente por unidade
     */
    public void setPricePerUnit(Double price) {
        this.pricePerUnit = price;
    }

    /**
     * Retorna o custo do componente por unidade
     * @return o custo do componente por unidade
     */
    public Double getCostPerUnit() {
        return costPerUnit;
    }

    /**
     * Define a quantidade do componente
     * @return a quantidade do componente
     */
    @Override
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Define o custo do componente por unidade
     * @param cost Custo do componente por unidade
     */
    public void setCostPerUnit(Double cost) {
        this.costPerUnit = cost;
    }

    @Override
    public String toString() {
        return "OtherComponent [costPerUnit=" + costPerUnit + ", name=" + name + ", pricePerUnit="
                + pricePerUnit + ", quantity=" + quantity + "]";
    }
}
