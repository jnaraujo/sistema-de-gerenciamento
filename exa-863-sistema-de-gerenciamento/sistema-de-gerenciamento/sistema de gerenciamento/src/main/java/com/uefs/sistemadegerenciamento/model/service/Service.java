package com.uefs.sistemadegerenciamento.model.service;

/**
 * <p>
 * Interface que representa um serviço prestado.
 * Os serviços podem ser de construção, limpeza ou instalação.
 * </p>
 *
 * <p>
 * Eles serão usados nas {@link com.uefs.sistemadegerenciamento.model.WorkOrder ordens de serviço}.
 * </p>
 *
 * @see BuildingService
 * @see CleaningService
 * @see InstallationService
 * @see com.uefs.sistemadegerenciamento.model.WorkOrder
 */
public interface Service {
    /**
     * Retorna o preço do serviço
     * @return o preço do serviço
     */
    public double getPrice();

    /**
     * Retorna o custo do serviço
     * @return o custo do serviço
     */
    public double getCost() ;
}
