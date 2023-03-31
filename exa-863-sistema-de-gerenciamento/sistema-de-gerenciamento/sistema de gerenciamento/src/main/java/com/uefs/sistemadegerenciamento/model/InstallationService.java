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

    public InstallationService(double price, double cost) {
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
     * @return Retorna o custo do serviço
     */
    @Override
    public double getCost() {
        return this.cost;
    }
}
