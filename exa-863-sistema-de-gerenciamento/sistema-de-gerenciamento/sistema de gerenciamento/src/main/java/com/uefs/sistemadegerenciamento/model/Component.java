package com.uefs.sistemadegerenciamento.model;

/**
 * Interface que representa um componente
 */
public interface Component {
    /**
     * @return Retorna o preço do componente
     */
    Double getPricePerUnit();

    /**
     * @return Retorna o custo do componente
     */
    Double getCostPerUnit();

    /**
     * @return Retorna a quantidade do componente
     */
    Integer getQuantity();
}
