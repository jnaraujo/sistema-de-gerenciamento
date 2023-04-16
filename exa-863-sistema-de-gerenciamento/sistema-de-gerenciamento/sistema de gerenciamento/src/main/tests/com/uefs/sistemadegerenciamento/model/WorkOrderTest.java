package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.errors.InvalidSatisfactionScoreExeption;
import com.uefs.sistemadegerenciamento.errors.ServiceOrderWithoutTechnicianException;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import com.uefs.sistemadegerenciamento.model.service.BuildingService;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.model.service.Service;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
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
                "urgent work order",
                customerId
        );
        workOrder.setId("1");
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
        assertEquals(null, workOrder.getPaymentMethod());
        assertEquals(0, workOrder.getSatisfactionScore());
    }

    @Test
    void testSetTechnician() {
        assertNull(workOrder.getTechnicianId());

        workOrder.setTechnicianId(technicianId);

        assertEquals(this.technicianId, workOrder.getTechnicianId());
    }

    @Test
    void testWorkOrderFinishWithoutTechnician() {
        assertThrows(ServiceOrderWithoutTechnicianException.class, workOrder::finish);
    }

    @Test
    void testWorkOrderFinish() throws ServiceOrderWithoutTechnicianException {
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
                "customer is complaining",
                customerId
        );
        workOrder.setId("1");

        workOrder.addService(new CleaningService(
                100.00,
                50.00
        ));

        workOrder.addService(new InstallationService(
                "Windows 10",
                200.00,
                100.00
        ));

        BuildingService buildingService = new BuildingService();
        buildingService.addComponent(new ComputerComponent(
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

    @Test
    void testToString(){
        String expected = "WorkOrder [id=1, customerId=" + customerId + ", technicianId=null, status=Em andamento, " +
                "description=urgent work order, services=[], createdAt=" + workOrder.getCreatedAt() + ", finishedAt" +
                "=null]";
        assertEquals(expected, workOrder.toString());
    }

    @Test
    void testValidSatisfactionScore() throws InvalidSatisfactionScoreExeption {
        assertEquals(0, workOrder.getSatisfactionScore());

        workOrder.setSatisfactionScore(5);
        assertEquals(5, workOrder.getSatisfactionScore());
    }

    @Test
    void testInvalidSatisfactionScore(){
        assertThrows(InvalidSatisfactionScoreExeption.class, () -> workOrder.setSatisfactionScore(6));
        assertThrows(InvalidSatisfactionScoreExeption.class, () -> workOrder.setSatisfactionScore(-1));
    }
}