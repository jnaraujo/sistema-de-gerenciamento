package com.uefs.sistemadegerenciamento.dao.workorder;

import com.uefs.sistemadegerenciamento.constants.OrderStatus;
import com.uefs.sistemadegerenciamento.dao.FileManager;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *     Implementação de {@link WorkOrderDao} que armazena os dados em disco.
 * </p>
 *
 * @see WorkOrderDao
 * @author Jônatas Araújo
 */
public class DiskWorkOrderDao implements WorkOrderDao{
    private HashMap<String, WorkOrder> workOrders;
    private FileManager<String, WorkOrder> fileManager;


    /**
     * Cria um novo {@link InMemoryWorkOrderDao}.
     */
    public DiskWorkOrderDao(String fileName) {
        workOrders = new HashMap<>();
        fileManager = new FileManager(fileName);
        workOrders = fileManager.load();
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

        fileManager.save(workOrders);
        return workOrder;
    }

    /**
     * Deleta uma ordem de serviço pelo ID.
     * @param id ID da ordem de serviço a ser deletada.
     */
    @Override
    public void delete(String id) {
        workOrders.remove(id);
        fileManager.save(workOrders);
    }

    /**
     * Atualiza uma ordem de serviço.
     * @param workOrder Ordem de serviço a ser atualizada.
     */
    @Override
    public void update(WorkOrder workOrder) {
        workOrders.replace(workOrder.getId(), workOrder);
        fileManager.save(workOrders);
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
        fileManager.save(workOrders);
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
            if (workOrder.getStatus().equals(OrderStatus.OPEN) && workOrder.getTechnicianId() == null) {
                openWorkOrders.add(workOrder);
            }
        }
        openWorkOrders.sort(Comparator.comparing(WorkOrder::getCreatedAt));
        return openWorkOrders;
    }

    /**
     * Busca a primeira ordem de serviço aberta que não possui técnico.
     * @return A primeira ordem de serviço aberta.
     */
    @Override
    public WorkOrder findFirstOpenWorkOrder() {
        List<WorkOrder> openWorkOrdersWithNoTechnician = findOpenWorkOrders().stream().filter(workOrder -> workOrder.getTechnicianId() == null).toList();

        if (openWorkOrdersWithNoTechnician.isEmpty()) {
            return null;
        }

        return openWorkOrdersWithNoTechnician.get(0);
    }

    /**
     * Busca a ordem de serviço aberta de um técnico.
     * @param technicianId ID do técnico.
     * @return A ordem de serviço do técnico com o ID informado.
     */
    @Override
    public WorkOrder findOpenOrderByTechnicianId(String technicianId) {
        for (WorkOrder workOrder : workOrders.values()) {
            if(workOrder.getTechnicianId() == null){
                continue;
            }
            if(!workOrder.getTechnicianId().equals(technicianId)){
                continue;
            }
            if(!workOrder.getStatus().equals(OrderStatus.OPEN)){
                continue;
            }

            return workOrder;
        }
        return null;
    }

    /**
     * Busca todas as ordens de serviço de um cliente.
     * @param customerId ID do cliente
     * @return Todas as ordens de serviço de um cliente.
     */
    @Override
    public List<WorkOrder> findAllWorkOrdersByCustomer(String customerId) {
        return workOrders.values().stream().filter(workOrder -> workOrder.getCustomerId().equals(customerId)).toList();
    }

    /**
     * Retorna o tempo médio em horas para reparar uma ordem de serviço.
     * @return O tempo médio em horas para reparar uma ordem de serviço.
     */
    @Override
    public Double getAverageTimeInHoursToRepair() {
        double sum = 0;
        int count = 0;

        for(WorkOrder order : workOrders.values()){
            if(order.getFinishedAt() == null){
                continue;
            }
            if(order.getCreatedAt() == null){
                continue;
            }

            long diff = order.getFinishedAt().getTime() - order.getCreatedAt().getTime();
            sum += diff;
            count++;
        }

        int HOURS_IN_MILLIS = 60 * 60 * 1000;
        double hours = sum / HOURS_IN_MILLIS;
        return hours / count;
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
    public Double getAverageCustomerSatisfaction() {
        double sum = 0;
        int count = 0;

        for(WorkOrder order : workOrders.values()){
            if(!order.getStatus().equals(OrderStatus.CLOSED)){
                continue;
            }
            sum += order.getSatisfactionScore();
            count++;
        }
        return sum / count;
    }
}
