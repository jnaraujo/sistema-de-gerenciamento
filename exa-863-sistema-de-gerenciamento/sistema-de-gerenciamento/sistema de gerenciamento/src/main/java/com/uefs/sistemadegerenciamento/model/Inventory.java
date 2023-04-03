package com.uefs.sistemadegerenciamento.model;


import java.util.ArrayList;

/**
 * Classe que representa um inventário de componentes
 */
public class Inventory {
    private ArrayList<ComputerComponent> components = new ArrayList<>();

    void addComponent(ComputerComponent component) {
        components.add(component);
    }

    void removeComponent(ComputerComponent component) {
        components.remove(component);
    }

    ArrayList<ComputerComponent> getComponents() {
        return components;
    }

    void setComponents(ArrayList<ComputerComponent> components) {
        this.components = components;
    }

}
