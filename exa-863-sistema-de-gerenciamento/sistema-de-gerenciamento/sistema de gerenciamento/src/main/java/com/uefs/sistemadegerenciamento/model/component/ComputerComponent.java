package com.uefs.sistemadegerenciamento.model.component;

/**
 * Classe que representa um componente de computador
 */
public class ComputerComponent implements Component {
    private String id;
    private String name;
    private String manufacturer;
    private Double pricePerUnit;
    private Double costPerUnit;
    private Integer quantity;

    public ComputerComponent(String id, String name, String manufacturer, Double pricePerUnit, Double costPerUnit,
                             Integer quantity) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.pricePerUnit = pricePerUnit;
        this.costPerUnit = costPerUnit;
        this.quantity = quantity;
    }

    /**
     * Retorna o id do componente
     * @return Retorna o id do componente
     */
    public String getId() {
        return id;
    }

    /**
     * Define o id do componente
     * @param id Id do componente
     */
    public void setId(String id) {
        this.id = id;
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
     * Retorna o custo do componente
     * @return Retorna o custo do componente
     */
    public Double getCostPerUnit() {
        return costPerUnit;
    }

    /**
     * Define o custo do componente
     * @param cost Custo do componente
     */
    public void setCostPerUnit(Double cost) {
        this.costPerUnit = cost;
    }

    /**
     * Retorna a quantidade do componente
     * @return Retorna a quantidade do componente
     */
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ComputerComponent)) return false;

        ComputerComponent component = (ComputerComponent) obj;

        return this.id.equals(component.id);
    }
}
