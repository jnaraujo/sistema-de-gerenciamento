package com.uefs.sistemadegerenciamento.model.component;

/**
 * Interface que representa um componente do sistema.
 * Um componente é qualquer objeto que pode ser adicionado a um produto.
 *
 * @see ComputerComponent
 * @see OtherComponent
 */
public interface Component {
    /**
     * Retorna o nome do componente.
     * @return o preço do componente
     */
    Double getPricePerUnit();

    /**
     * Retorna o custo do componente.
     * @return o custo do componente
     */
    Double getCostPerUnit();

    /**
     * Retorna a quantidade do componente.
     * @return a quantidade do componente
     */
    Integer getQuantity();
}
