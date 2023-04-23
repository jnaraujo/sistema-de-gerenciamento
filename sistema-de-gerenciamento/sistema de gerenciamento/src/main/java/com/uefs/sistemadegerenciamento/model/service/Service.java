package com.uefs.sistemadegerenciamento.model.service;

import java.io.Serializable;

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
 * @author Jônatas Araújo
 */
public interface Service extends Serializable{
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
