package com.uefs.sistemadegerenciamento.dao.workorder;

import com.uefs.sistemadegerenciamento.dao.Dao;
import com.uefs.sistemadegerenciamento.model.WorkOrder;

import java.util.List;

public interface WorkOrderDao extends Dao<WorkOrder> {
    /**
     * Retorna uma lista de ordens de serviço abertas por ordem de criação (mais antiga primeiro)
     * @return Lista de ordens de serviço abertas
     */
    List<WorkOrder> findOpenWorkOrders();

    /**
     * @param technicianId ID do técnico
     * @return Retorna a ordem de serviço do técnico
     */
    WorkOrder findOrderByTechnicianId(String technicianId);
}
