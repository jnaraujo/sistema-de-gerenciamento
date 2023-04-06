package com.uefs.sistemadegerenciamento.dao.workorder;

import com.uefs.sistemadegerenciamento.dao.Dao;
import com.uefs.sistemadegerenciamento.model.WorkOrder;

import java.util.List;

public interface WorkOrderDao extends Dao<WorkOrder> {
    List<WorkOrder> findOpenWorkOrders();
}
