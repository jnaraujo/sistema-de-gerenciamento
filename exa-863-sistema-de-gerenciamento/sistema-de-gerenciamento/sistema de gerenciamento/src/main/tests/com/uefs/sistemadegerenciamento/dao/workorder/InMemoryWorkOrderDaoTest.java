package com.uefs.sistemadegerenciamento.dao.workorder;

import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.Technician;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryWorkOrderDaoTest {

    private Customer customer;
    private WorkOrder workOrder;
    private InMemoryWorkOrderDao workOrderDao;

    @BeforeEach
    void setUp() {
        customer = new Customer(
                UUID.randomUUID().toString(),
                "João da Silva",
                "Rua A",
                "75 9 1234-5678",
                "joaodasilva@test.com"
        );
        workOrder = new WorkOrder(
                UUID.randomUUID().toString(),
                "O cliente está reclamando de um problema no sistema",
                customer
        );
        workOrderDao = new InMemoryWorkOrderDao();
    }

    @Test
    void testSave() {
        workOrderDao.save(workOrder);
        assertEquals(workOrder, workOrderDao.findById(workOrder.getId()));
    }

    @Test
    void testDelete(){
        workOrderDao.save(workOrder);
        assertNotNull(workOrderDao.findById(workOrder.getId()));
        workOrderDao.delete(workOrder.getId());
        assertNull(workOrderDao.findById(workOrder.getId()));
    }

    @Test
    void testUpdate(){
        workOrderDao.save(workOrder);
        assertNull(workOrderDao.findById(workOrder.getId()).getTechnician());

        Technician technician = new Technician(UUID.randomUUID().toString(), "José Marcio", "josemarcio@test.com");
        workOrder.setTechnician(technician);

        workOrderDao.update(workOrder);

        assertEquals(technician, workOrderDao.findById(workOrder.getId()).getTechnician());
    }

    @Test
    void testGetAll(){
        List<WorkOrder> orders = new ArrayList<>();
        orders.add(
                new WorkOrder(
                        UUID.randomUUID().toString(),
                        "another work order",
                        customer
                ));
        orders.add(
                new WorkOrder(
                        UUID.randomUUID().toString(),
                        "yet another work order",
                        customer
                )
        );
        orders.add(workOrder);

        for(WorkOrder order : orders){
            workOrderDao.save(order);
        }

        for(WorkOrder order : orders){
            assertTrue(workOrderDao.getAll().contains(order));
        }
    }

}