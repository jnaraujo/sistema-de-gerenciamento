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

    @Override
    public Double getAverageTimeInHoursToRepair() {
        double sum = 0;

        for(WorkOrder order : workOrders.values()){
            long diff = order.getFinishedAt().getTime() - order.getCreatedAt().getTime();
            sum += diff;
        }

        double hours = sum / (60 * 60 * 1000);

        return hours / workOrders.size();
    }

    @Override
    public Double getAverageTimeInHoursToRepairByTechnician(String technicianId) {
        double hours = 0;
        int count = 0;

        for(WorkOrder order : workOrders.values()){
            if(order.getTechnicianId().equals(technicianId)){
                long diff = order.getFinishedAt().getTime() - order.getCreatedAt().getTime();
                hours += diff;
                count++;
            }
        }

        hours = hours / (60 * 60 * 1000);

        return hours / count;
    }

    @Override
    public Double getAverageWorkOrderCost() {
        double sum = 0;
        for(WorkOrder order : workOrders.values()){
            sum += order.getCost();
        }
        return sum / workOrders.size();
    }

    @Override
    public Double getAverageWorkOrderPrice() {
        double sum = 0;
        for(WorkOrder order : workOrders.values()){
            sum += order.getPrice();
        }
        return sum / workOrders.size();
    }

    @Override
    public Double getAverageCustomerSatifaction() {
        double sum = 0;
        for(WorkOrder order : workOrders.values()){
            sum += order.getSatisfactionScore();
        }
        return sum / workOrders.size();
    }
}
