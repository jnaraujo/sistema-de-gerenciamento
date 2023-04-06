package com.uefs.sistemadegerenciamento.model;

import java.util.List;

public class Report {
    private List<WorkOrder> workOrders;
    private List<ComputerComponent> components;
    private static int HOURS_IN_MILLISECONDS = 60 * 60 * 1000;
    public Report(List<WorkOrder> workOrders, List<ComputerComponent> components ){
        if(workOrders.size() == 0) throw new IllegalArgumentException("Work orders list cannot be empty");
        if(components.size() == 0) throw new IllegalArgumentException("Components list cannot be empty");

        this.workOrders = workOrders;
        this.components = components;
    }

    public Double getAvarageTimeInHoursToRepair(){
        Double hours = 0.0;
        for (WorkOrder workOrder : workOrders) {
            long diff = workOrder.getFinishedAt().getTime() - workOrder.getCreatedAt().getTime();
            hours += (double) diff / HOURS_IN_MILLISECONDS;
        }

        return hours / workOrders.size();
    }

    public Double getAvarageTimeInHoursToRepairByTechnician(String technicianId){
        Double hours = 0.0;
        int count = 0;
        for (WorkOrder workOrder : workOrders) {
            if(workOrder.getTechnicianId() == null) continue;

            if (workOrder.getTechnicianId().equals(technicianId)){
                long diff = workOrder.getFinishedAt().getTime() - workOrder.getCreatedAt().getTime();
                hours += (double) diff / HOURS_IN_MILLISECONDS;
                count++;
            }
        }

        return hours / count;
    }

    public Double getAvarageCost(){
        Double sum = 0.0;
        for (WorkOrder workOrder : workOrders) {
            sum += workOrder.getCost();
        }
        return sum / workOrders.size();
    }

    public Double getAvarageWorkOrderCost(){
        Double sum = 0.0;
        for (WorkOrder workOrder : workOrders) {
            sum += workOrder.getCost();
        }
        return sum / workOrders.size();
    }

    public Double getAvarageWorkOrderPrice(){
        Double sum = 0.0;
        for (WorkOrder workOrder : workOrders) {
            sum += workOrder.getPrice();
        }
        return sum / workOrders.size();
    }

    public Double getAvarageComponentCost(){
        Double sum = 0.0;
        int quantity = 0;
        for (ComputerComponent component : components) {
            sum += component.getCostPerUnit() * component.getQuantity();
            quantity += component.getQuantity();
        }
        return sum / quantity;
    }

    public Double getAvarageComponentPrice(){
        Double sum = 0.0;
        int quantity = 0;
        for (ComputerComponent component : components) {
            sum += component.getPricePerUnit() * component.getQuantity();
            quantity += component.getQuantity();
        }
        return sum / quantity;
    }

}
