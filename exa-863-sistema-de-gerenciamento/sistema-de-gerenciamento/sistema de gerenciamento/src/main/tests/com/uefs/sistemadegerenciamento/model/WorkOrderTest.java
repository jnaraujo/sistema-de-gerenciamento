package com.uefs.sistemadegerenciamento.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkOrderTest {
    Customer customer;
    Technician technician;
    WorkOrder workOrder;

    @BeforeEach
    void setUp() {
        customer = new Customer(
                "1",
                "John Doe",
                "123 Main St",
                "555-555-5555",
                "teste@test.com"
        );

        technician = new Technician(
                "2",
                "João da Silva",
                "joao@test.com"
        );

        workOrder = new WorkOrder(
                "1",
                "urgent work order",
                customer
        );
    }


    @Test
    void testWorkOrderCreation() {
        assertNotNull(workOrder);
        assertEquals("1", workOrder.getId());
        assertEquals("urgent work order", workOrder.getDescription());
        assertEquals(customer, workOrder.getCustomer());
        assertEquals("Em andamento", workOrder.getStatus());
        assertEquals(0, workOrder.getServices().size());
        assertFalse(workOrder.isFinished());
        assertFalse(workOrder.isCanceled());
        assertTrue(workOrder.isOngoing());
        assertNotNull(workOrder.getCreatedAt());
        assertNull(workOrder.getFinishedAt());
    }

    @Test
    void testSetTechnician() {
        assertNull(workOrder.getTechnician());

        workOrder.setTechnician(this.technician);

        assertEquals(this.technician, workOrder.getTechnician());
    }

    @Test
    void testWorkOrderFinishWithoutTechnician() {
        assertThrows(IllegalStateException.class, workOrder::finish);
    }

    @Test
    void testWorkOrderFinish() {
        workOrder.setTechnician(this.technician);

        workOrder.finish();

        assertEquals("Finalizado", workOrder.getStatus());
        assertNotNull(workOrder.getFinishedAt());
    }

    @Test
    void testWorkOrderCancel() {
        workOrder.cancel();

        assertEquals("Cancelado", workOrder.getStatus());
        assertNotNull(workOrder.getFinishedAt());
    }

    @Test
    void testWorkOrderAddService() {
        WorkOrder workOrder = new WorkOrder(
                "1",
                "customer is complaining",
                this.customer
        );

        workOrder.addService(new CleaningService(
                100.00,
                50.00
        ));

        workOrder.addService(new InstallationService(
                200.00,
                100.00
        ));

        BuildingService buildingService = new BuildingService();
        buildingService.addComponent(new ComputerComponent(
                UUID.randomUUID().toString(),
                "HYPERX 8GB 2400MHz",
                "HYPERX",
                200.0,
                100.0,
                3
        ));

        workOrder.addService(buildingService);

        List<Service> services = workOrder.getServices();

        assertEquals(3, services.size());
        assertEquals(900.0, workOrder.getPrice());
        assertEquals(450.0, workOrder.getCost());
    }
}