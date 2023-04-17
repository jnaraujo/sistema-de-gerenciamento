package com.uefs.sistemadegerenciamento.dao.workorder;

import com.uefs.sistemadegerenciamento.dao.Dao;
import com.uefs.sistemadegerenciamento.model.WorkOrder;

import java.util.List;

/**
 * Interface que define os métodos de acesso a dados de ordens de serviço.
 * @see WorkOrder
 */
public interface WorkOrderDao extends Dao<WorkOrder> {
    /**
     * Retorna uma lista de ordens de serviço abertas por ordem de criação (mais antiga primeiro)
     * @return lista de ordens de serviço abertas
     */
    List<WorkOrder> findOpenWorkOrders();

    /**
     * @param technicianId ID do técnico
     * @return ordem de serviço do técnico
     */
    WorkOrder findOrderByTechnicianId(String technicianId);

    /**
     * Retorna o tempo médio de espera para reparo de uma ordem de serviço
     * @return Tempo médio de espera para reparo de uma ordem de serviço
     */
    Double getAverageTimeInHoursToRepair();

    /**
     * Retorna o tempo médio de espera para reparo de uma ordem de serviço por técnico
     * @param technicianId ID do técnico
     * @return tempo médio de espera para reparo de uma ordem de serviço por técnico
     */
    Double getAverageTimeInHoursToRepairByTechnician(String technicianId);

    /**
     * Retorna o custo médio de uma ordem de serviço
     * @return custo médio de uma ordem de serviço
     */
    Double getAverageWorkOrderCost();

    /**
     * Retorna o preço médio de uma ordem de serviço
     * @return preço médio de uma ordem de serviço
     */
    Double getAverageWorkOrderPrice();

    /**
     * Retorna a satisfação média dos clientes
     * @return satisfação média dos clientes
     */
    Double getAverageCustomerSatifaction();
}
