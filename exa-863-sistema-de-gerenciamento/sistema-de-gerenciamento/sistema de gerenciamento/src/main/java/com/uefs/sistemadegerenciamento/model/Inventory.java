package com.uefs.sistemadegerenciamento.model;


import java.util.ArrayList;

/**
 * Classe que representa um inventário de componentes
 */
public class Inventory {
    private ArrayList<ComputerComponent> components = new ArrayList<>();

    /**
     * @param component Componente a ser adicionado ao inventário
     */
    void addComponent(ComputerComponent component) {
        components.add(component);
    }

    /**
     * @param component Componente a ser removido do inventário
     */
    void removeComponent(ComputerComponent component) {
        components.remove(component);
    }

    /**
     * @return Retorna uma lista com todos os componentes do inventário
     */
    ArrayList<ComputerComponent> getComponents() {
        return components;
    }

    /**
     * @param components Lista de componentes a serem adicionados ao inventário
     */
    void setComponents(ArrayList<ComputerComponent> components) {
        this.components = components;
    }

}
