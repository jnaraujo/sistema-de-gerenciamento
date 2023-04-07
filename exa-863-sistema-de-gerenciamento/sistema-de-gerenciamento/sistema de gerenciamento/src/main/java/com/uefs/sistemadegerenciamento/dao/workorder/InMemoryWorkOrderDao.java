package com.uefs.sistemadegerenciamento.dao.workorder;

import com.uefs.sistemadegerenciamento.constants.OrderStatus;
import com.uefs.sistemadegerenciamento.model.WorkOrder;

import java.util.*;

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

    @Override
    public List<WorkOrder> findOpenWorkOrders() {
        List<WorkOrder> openWorkOrders = new ArrayList<>();
        for (WorkOrder workOrder : workOrders.values()) {
            if (workOrder.getStatus().equals(OrderStatus.OPEN)) {
                openWorkOrders.add(workOrder);
            }
        }
        openWorkOrders.sort(Comparator.comparing(WorkOrder::getCreatedAt));
        return openWorkOrders;
    }

    @Override
    public WorkOrder findOrderByTechnicianId(String technicianId) {
        for (WorkOrder workOrder : workOrders.values()) {
            if (workOrder.getTechnicianId().equals(technicianId)) {
                return workOrder;
            }
        }
        return null;
    }
}
