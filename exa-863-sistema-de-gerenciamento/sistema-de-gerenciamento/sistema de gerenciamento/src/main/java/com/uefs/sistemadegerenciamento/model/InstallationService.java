package com.uefs.sistemadegerenciamento.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um serviço de instalação de programas e/ou sistemas operacionais
 */
public class InstallationService implements Service{
    private final List<String> programs;
    private String operatingSystem;
    private double price;
    private double cost;
    private String id;

    public InstallationService(String id, double price, double cost) {
        this.id = id;
        this.programs = new ArrayList<>();
        this.price = price;
        this.cost = cost;
    }

    /**
     * @return Retorna a lista de programas a serem instalados
     */
    public List<String> getPrograms() {
        return programs;
    }

    /**
     * Adiciona um programa à lista de programas a serem instalados
     * @param program Programa a ser adicionado à lista de programas a serem instalados
     */
    public void addProgram(String program) {
        this.programs.add(program);
    }

    /**
     * @return Retorna o sistema operacional a ser instalado
     */
    public String getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * Define o sistema operacional a ser instalado
     * @param operatingSystem Sistema operacional a ser instalado
     */
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
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


}
