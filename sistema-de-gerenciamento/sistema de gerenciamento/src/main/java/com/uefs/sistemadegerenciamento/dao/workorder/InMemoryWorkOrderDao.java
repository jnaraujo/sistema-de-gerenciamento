package com.uefs.sistemadegerenciamento.dao.workorder;

import com.uefs.sistemadegerenciamento.constants.OrderStatus;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.*;

/**
 * <p>
 *     Implementação de {@link WorkOrderDao} que armazena os dados em memória.
 * </p>
 *
 * @see WorkOrderDao
 * @author Jônatas Araújo
 */
public class InMemoryWorkOrderDao implements WorkOrderDao {
    private HashMap<String, WorkOrder> workOrders;

    /**
     * Cria um novo {@link InMemoryWorkOrderDao}.
     */
    public InMemoryWorkOrderDao() {
        workOrders = new HashMap<>();
    }


    /**
     * Salva uma ordem de serviço.
     * @param workOrder Ordem de serviço a ser salva.
     * @return Ordem de serviço salva com o ID gerado.
     */
    @Override
    public WorkOrder save(WorkOrder workOrder) {
        String id = IdGenerator.generate();
        workOrder.setId(id);

        workOrders.put(workOrder.getId(), workOrder);

        return workOrder;
    }

    /**
     * Deleta uma ordem de serviço pelo ID.
     * @param workOrderID ID da ordem de serviço a ser deletada.
     */
    @Override
    public void delete(String workOrderID) {
        workOrders.remove(workOrderID);
    }

    /**
     * Atualiza uma ordem de serviço.
     * @param workOrder Ordem de serviço a ser atualizada.
     */
    @Override
    public void update(WorkOrder workOrder) {
        workOrders.replace(workOrder.getId(), workOrder);
    }

    /**
     * Busca uma ordem de serviço pelo ID.
     * @param id ID da ordem de serviço a ser buscada.
     * @return Ordem de serviço com o ID informado.
     */
    @Override
    public WorkOrder findById(String id) {
        return workOrders.get(id);
    }

    /**
     * Busca todas as ordens de serviço.
     * @return Todas as ordens de serviço.
     */
    @Override
    public List<WorkOrder> getAll() {
        return new ArrayList<>(workOrders.values());
    }

    /**
     * Deleta todas as ordens de serviço.
     */
    @Override
    public void deleteAll() {
        workOrders.clear();
    }

    /**
     * <p>
     *     Busca todas as ordens de serviço abertas.
     * </p>
     * <p>
     *     Ordens de serviço são consideradas abertas quando seu status é {@link OrderStatus#OPEN}.
     * </p>
     * @return Todas as ordens de serviço abertas.
     */
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

    /**
     * Busca a ordem de serviço do técnico com o ID informado.
     * @param technicianId ID do técnico.
     * @return A ordem de serviço do técnico com o ID informado.
     */
    @Override
    public WorkOrder findOrderByTechnicianId(String technicianId) {
        for (WorkOrder workOrder : workOrders.values()) {
            if (workOrder.getTechnicianId().equals(technicianId)) {
                return workOrder;
            }
        }
        return null;
    }

    /**
     * Retorna o tempo médio em horas para reparar uma ordem de serviço.
     * @return O tempo médio em horas para reparar uma ordem de serviço.
     */
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

    /**
     * Retorna o tempo médio que um técnico demora para reparar uma ordem de serviço.
     * @param technicianId ID do técnico.
     * @return O tempo médio que um técnico demora para reparar uma ordem de serviço.
     */
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

    /**
     * Retorna o custo médio de uma ordem de serviço.
     * @return O custo médio de uma ordem de serviço.
     */
    @Override
    public Double getAverageWorkOrderCost() {
        double sum = 0;
        for(WorkOrder order : workOrders.values()){
            sum += order.getCost();
        }
        return sum / workOrders.size();
    }

    /**
     * Retorna o preço médio de uma ordem de serviço.
     * @return O preço médio de uma ordem de serviço.
     */
    @Override
    public Double getAverageWorkOrderPrice() {
        double sum = 0;
        for(WorkOrder order : workOrders.values()){
            sum += order.getPrice();
        }
        return sum / workOrders.size();
    }

    /**
     * Retorna a satisfacao média de uma ordem de serviço.
     * @return A satisfacao média de uma ordem de serviço.
     */
    @Override
    public Double getAverageCustomerSatifaction() {
        double sum = 0;
        for(WorkOrder order : workOrders.values()){
            sum += order.getSatisfactionScore();
        }
        return sum / workOrders.size();
    }
}
