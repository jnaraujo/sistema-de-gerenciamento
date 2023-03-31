package com.uefs.sistemadegerenciamento.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe que representa uma ordem de serviço
 */
public class WorkOrder {
    private final String id;
    private Customer customer;
    private Technician technician;
    private String status = "Em andamento";
    private String description;
    private final List<Service> services;
    private Date createdAt;
    private Date finishedAt;

    public WorkOrder(
        String id,
        String description,
        Customer customer
    ) {
        this.id = id;
        this.customer = customer;
        this.description = description;

        this.services = new ArrayList<>();
        this.createdAt = new Date();
    }

    /**
     * @return Retorna a lista de serviços cadastrados na ordem de serviço
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * Adiciona um serviço na ordem de serviço
     * @param service Serviço a ser adicionado
     */
    public void addService(Service service) {
        this.services.add(service);
    }

    /**
     * Retorna o preço total da ordem de serviço
     * baseado nos serviços cadastrados
     * @return Preço total da ordem de serviço
     */
    public double getPrice() {
        double price = 0;
        for (Service service : this.services) {
            price += service.getPrice();
        }
        return price;
    }
    /**
     * Retorna o custo total da ordem de serviço
     * baseado nos serviços cadastrados
     * @return Custo total da ordem de serviço
     */
    public double getCost() {
        double cost = 0;
        for (Service service : this.services) {
            cost += service.getCost();
        }
        return cost;
    }

    /**
     * Finaliza a ordem de serviço
     * @throws IllegalStateException Caso a ordem de serviço não tenha um técnico
     */
    public void finish() throws IllegalStateException{
        if(this.technician == null)
            throw new IllegalStateException("Não é possível finalizar uma ordem de serviço sem um técnico");

        this.status = "Finalizado";
        this.finishedAt = new Date();
    }

    /**
     * Cancela a ordem de serviço
     */
    public void cancel() {
        this.status = "Cancelado";
        this.finishedAt = new Date();
    }

    /**
     * @return Retorna true se a ordem de serviço estiver finalizada
     */
    public boolean isFinished() {
        return this.status.equals("Finalizado");
    }

    /**
     * @return Retorna true se a ordem de serviço estiver cancelada
     */
    public boolean isCanceled() {
        return this.status.equals("Cancelado");
    }

    /**
     * @return Retorna true se a ordem de serviço estiver em andamento
     */
    public boolean isOngoing() {
        return this.status.equals("Em andamento");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WorkOrder)) {
            return false;
        }
        WorkOrder workOrder = (WorkOrder) obj;
        return workOrder.getId().equals(this.id);
    }

    // getters and setters

    /**
     * @return Retorna o id da ordem de serviço
     */
    public String getId() {
        return id;
    }

    /**
     * @return Retorna o cliente da ordem de serviço
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Altera o cliente da ordem de serviço
     * @param customer Novo cliente da ordem de serviço
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return Retorna o técnico da ordem de serviço
     */
    public Technician getTechnician() {
        return technician;
    }

    /**
     * Altera o técnico da ordem de serviço
     * @param technician Novo técnico da ordem de serviço
     */
    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    /**
     * @return Retorna o status da ordem de serviço
     */
    public String getStatus() {
        return status;
    }

    /**
     * Altera o status da ordem de serviço
     * @param status Novo status da ordem de serviço
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Retorna a descrição da ordem de serviço
     */
    public String getDescription() {
        return description;
    }

    /**
     * Altera a descrição da ordem de serviço
     * @param description Nova descrição da ordem de serviço
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Retorna a data de criação da ordem de serviço
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Altera a data de criação da ordem de serviço
     * @param createdAt Nova data de criação da ordem de serviço
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return Retorna a data de finalização da ordem de serviço
     */
    public Date getFinishedAt() {
        return finishedAt;
    }

    /**
     * Altera a data de finalização da ordem de serviço
     * @param finishedAt Nova data de finalização da ordem de serviço
     */
    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }
}
