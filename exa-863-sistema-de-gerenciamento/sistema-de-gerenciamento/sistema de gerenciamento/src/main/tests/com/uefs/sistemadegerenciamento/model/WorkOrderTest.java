package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkOrderTest {
    private String customerId;
    private String technicianId;
    WorkOrder workOrder;

    @BeforeEach
    void setUp() {
        customerId = IdGenerator.generate();
        technicianId = IdGenerator.generate();
        workOrder = new WorkOrder(
                "1",
                "urgent work order",
                customerId
        );
    }


    @Test
    void testWorkOrderCreation() {
        assertNotNull(workOrder);
        assertEquals("1", workOrder.getId());
        assertEquals("urgent work order", workOrder.getDescription());
        assertEquals(customerId, workOrder.getCustomerId());
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
        assertNull(workOrder.getTechnicianId());

        workOrder.setTechnicianId(technicianId);

        assertEquals(this.technicianId, workOrder.getTechnicianId());
    }

    @Test
    void testWorkOrderFinishWithoutTechnician() {
        assertThrows(IllegalStateException.class, workOrder::finish);
    }

    @Test
    void testWorkOrderFinish() {
        workOrder.setTechnicianId(this.technicianId);

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
                customerId
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