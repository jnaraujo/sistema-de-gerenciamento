package com.uefs.sistemadegerenciamento.model;

/**
 * Interface que representa um serviço
 */
public interface Service {
    /**
     * @return Retorna o preço do serviço
     */
    public double getPrice();

    /**
     * @return Retorna o custo do serviço
     */
    public double getCost() ;
}
