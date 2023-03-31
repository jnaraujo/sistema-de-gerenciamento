package com.uefs.sistemadegerenciamento.model;

/**
 * Interface que representa um componente
 */
public interface Component {
    /**
     * @return Retorna o preço do componente
     */
    Double getPrice();

    /**
     * @return Retorna o custo do componente
     */
    Double getCost();
}
