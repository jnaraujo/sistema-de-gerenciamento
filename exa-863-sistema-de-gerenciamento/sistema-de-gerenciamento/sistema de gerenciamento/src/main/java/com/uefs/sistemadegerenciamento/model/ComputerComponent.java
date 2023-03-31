package com.uefs.sistemadegerenciamento.model;

/**
 * Classe que representa um componente de computador
 */
public class ComputerComponent implements Component {
    private String name;
    private String manufacturer;
    private Double price;
    private Double cost;

    public ComputerComponent(String name, String manufacturer, Double price, Double cost) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.cost = cost;
    }

    /**
     * Retorna o nome do componente
     * @return Nome do componente
     */
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
     * Retorna o fabricante do componente
     * @return Retorna o fabricante do componente
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Define o fabricante do componente
     * @param manufacturer Nome do fabricante do componente
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Retorna o preço do componente
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
     * Retorna o custo do componente
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
