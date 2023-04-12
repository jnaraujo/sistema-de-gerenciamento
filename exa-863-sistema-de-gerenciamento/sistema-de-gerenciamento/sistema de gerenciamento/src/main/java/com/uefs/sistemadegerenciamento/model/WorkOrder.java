package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.constants.OrderStatus;
import com.uefs.sistemadegerenciamento.errors.InvalidSatisfactionScoreExeption;
import com.uefs.sistemadegerenciamento.errors.ServiceOrderWithoutTechnicianException;
import com.uefs.sistemadegerenciamento.model.service.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe que representa uma ordem de serviço
 */
public class WorkOrder {
    private String id;
    private String customerId;
    private String technicianId;
    private String status = OrderStatus.OPEN;
    private String description;
    private final List<Service> services;
    private Date createdAt;
    private Date finishedAt;
    private int satisfactionScore;

    public WorkOrder(
        String id,
        String description,
        String customerId
    ) {
        this.id = id;
        this.customerId = customerId;
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
     * @throws ServiceOrderWithoutTechnicianException Caso a ordem de serviço não tenha um técnico
     */
    public void finish() throws ServiceOrderWithoutTechnicianException {
        if(this.technicianId == null)
            throw new ServiceOrderWithoutTechnicianException("Não é possível finalizar uma ordem de serviço sem um técnico");

        this.status = OrderStatus.CLOSED;
        this.finishedAt = new Date();
    }

    /**
     * Cancela a ordem de serviço
     */
    public void cancel() {
        this.status = OrderStatus.CANCELED;
        this.finishedAt = new Date();
    }

    /**
     * @return Retorna true se a ordem de serviço estiver finalizada
     */
    public boolean isFinished() {
        return this.status.equals(OrderStatus.CLOSED);
    }

    /**
     * @return Retorna true se a ordem de serviço estiver cancelada
     */
    public boolean isCanceled() {
        return this.status.equals(OrderStatus.CANCELED);
    }

    /**
     * @return Retorna true se a ordem de serviço estiver em andamento
     */
    public boolean isOngoing() {
        return this.status.equals(OrderStatus.OPEN);
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
     * Altera o id da ordem de serviço
     * @param id Novo id da ordem de serviço
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Retorna o id do cliente da ordem de serviço
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Altera o id do cliente da ordem de serviço
     * @param customerId Novo id do cliente da ordem de serviço
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return Retorna o técnico da ordem de serviço
     */
    public String getTechnicianId() {
        return technicianId;
    }

    /**
     * Altera o técnico da ordem de serviço
     * @param technicianId Novo técnico da ordem de serviço
     */
    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
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

    /**
     * @return Retorna a nota de satisfação da ordem de serviço
     */
    public int getSatisfactionScore() {
        return satisfactionScore;
    }

    /**
     * Altera a nota de satisfação da ordem de serviço
     * @param satisfactionScore Nova nota de satisfação da ordem de serviço (deve estar entre 0 e 5)
        * @throws InvalidSatisfactionScoreExeption Caso a nota de satisfação não esteja entre 0 e 5
     */
    public void setSatisfactionScore(int satisfactionScore) throws InvalidSatisfactionScoreExeption {
        if(satisfactionScore < 0 || satisfactionScore > 5)
            throw new InvalidSatisfactionScoreExeption("A nota de satisfação deve estar entre 0 e 5");
        this.satisfactionScore = satisfactionScore;
    }

    @Override
    public String toString(){
        return "WorkOrder [id=" + id + ", customerId=" + customerId + ", technicianId=" + technicianId + ", status=" + status + ", " +
                "description=" + description + ", services=" + services + ", createdAt=" + createdAt + ", finishedAt=" + finishedAt + "]";
    }
}
