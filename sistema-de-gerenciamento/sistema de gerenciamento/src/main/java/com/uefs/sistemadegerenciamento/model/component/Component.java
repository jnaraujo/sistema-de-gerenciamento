package com.uefs.sistemadegerenciamento.model.component;

import java.io.Serializable;

/**
 * Interface que representa um componente do sistema.
 * Um componente é qualquer objeto que pode ser adicionado a um produto.
 *
 * @see ComputerComponent
 * @see OtherComponent
 * @author Jônatas Araújo
 */
public interface Component extends Serializable {

    /**
     * Retorna o nome do componente.
     * @return o nome do componente
     */
    String getName();

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
