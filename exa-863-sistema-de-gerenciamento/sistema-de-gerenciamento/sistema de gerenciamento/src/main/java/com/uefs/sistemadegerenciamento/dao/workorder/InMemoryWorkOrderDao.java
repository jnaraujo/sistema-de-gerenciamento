package com.uefs.sistemadegerenciamento.dao.workorder;

import com.uefs.sistemadegerenciamento.model.WorkOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryWorkOrderDao implements WorkOrderDao {
    private HashMap<String, WorkOrder> workOrders = new HashMap<>();
    @Override
    public void save(WorkOrder workOrder) {
        workOrders.put(workOrder.getId(), workOrder);
    }

    @Override
    public void delete(String workOrderID) {
        workOrders.remove(workOrderID);
    }

    @Override
    public void update(WorkOrder workOrder) {
        workOrders.replace(workOrder.getId(), workOrder);
    }

    @Override
    public WorkOrder findById(String id) {
        return workOrders.get(id);
    }

    @Override
    public List<WorkOrder> getAll() {
        return new ArrayList<>(workOrders.values());
    }

    @Override
    public void deleteAll() {
        workOrders.clear();
    }
}
