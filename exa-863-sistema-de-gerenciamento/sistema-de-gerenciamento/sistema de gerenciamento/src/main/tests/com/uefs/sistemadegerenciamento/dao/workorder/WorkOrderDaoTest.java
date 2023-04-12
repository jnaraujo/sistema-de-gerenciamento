package com.uefs.sistemadegerenciamento.dao.workorder;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.errors.InvalidSatisfactionScoreExeption;
import com.uefs.sistemadegerenciamento.errors.ServiceOrderWithoutTechnicianException;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WorkOrderDaoTest {
    private WorkOrderDao workOrderDao;
    private WorkOrder workOrder;


    @BeforeEach
    void setUp() {
        workOrderDao = DAOManager.getWorkOrderDao();
        workOrder = new WorkOrder(
                "O cliente está reclamando de um problema no sistema",
                IdGenerator.generate()
        );
        workOrder.setTechnicianId(IdGenerator.generate());
    }

    @AfterEach
    void tearDown() {
        workOrderDao.deleteAll();
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
        workOrder.setTechnicianId(null);
        workOrderDao.save(workOrder);
        assertNull(workOrderDao.findById(workOrder.getId()).getTechnicianId());

        String technician = IdGenerator.generate();
        workOrder.setTechnicianId(technician);

        workOrderDao.update(workOrder);

        assertEquals(technician, workOrderDao.findById(workOrder.getId()).getTechnicianId());
    }

    @Test
    void testGetAll(){
        List<WorkOrder> orders = new ArrayList<>();
        orders.add(
                new WorkOrder(
                        "another work order",
                        IdGenerator.generate()
                ));
        orders.add(
                new WorkOrder(
                        "yet another work order",
                        IdGenerator.generate()
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

    @Test
    void testFindOrderByTechnicianId(){
        workOrder.setTechnicianId(IdGenerator.generate());
        workOrderDao.save(workOrder);
        assertEquals(workOrder, workOrderDao.findOrderByTechnicianId(workOrder.getTechnicianId()));
    }

    @Test
    void testFindOrderByTechnicianIdNull(){
        assertNull(workOrderDao.findOrderByTechnicianId(workOrder.getTechnicianId()));
    }

    @Test
    void testFindOpenWorkOrders() throws ServiceOrderWithoutTechnicianException {
        Calendar calendar = Calendar.getInstance();

        WorkOrder workOrder2 = new WorkOrder(
                "another work order",
                IdGenerator.generate()
        );

        calendar.set(2020, Calendar.JANUARY, 5);
        workOrder2.setCreatedAt(calendar.getTime());

        WorkOrder workOrder3 = new WorkOrder(
                "yet another work order",
                IdGenerator.generate()
        );
        calendar.set(2020, Calendar.JANUARY, 1);
        workOrder3.setCreatedAt(calendar.getTime());

        WorkOrder workOrder4 = new WorkOrder(
                "yet another work order",
                IdGenerator.generate()
        );
        calendar.set(2020, Calendar.JANUARY, 3);
        workOrder4.setCreatedAt(calendar.getTime());

        workOrder.setTechnicianId(IdGenerator.generate());
        workOrder.finish();

        workOrder = workOrderDao.save(workOrder);
        workOrder2 = workOrderDao.save(workOrder2);
        workOrder3 = workOrderDao.save(workOrder3);
        workOrder4 = workOrderDao.save(workOrder4);

        List<WorkOrder> openOrders = workOrderDao.findOpenWorkOrders();

        assertEquals(workOrder3.getId(), openOrders.get(0).getId());
        assertEquals(workOrder4.getId(), openOrders.get(1).getId());
        assertEquals(workOrder2.getId(), openOrders.get(2).getId());

    }

    @Test
    void testGetAverageTimeInHoursToRepair() throws ServiceOrderWithoutTechnicianException {
        Calendar calendar = Calendar.getInstance();

        calendar.set(2020, Calendar.JANUARY, 1, 10, 0, 0);
        workOrder.setCreatedAt(calendar.getTime());
        calendar.set(2020, Calendar.JANUARY, 1, 11, 0, 0);
        workOrder.setFinishedAt(calendar.getTime()); // 1 hour

        WorkOrder workOrder2 = new WorkOrder(
                "another work order",
                IdGenerator.generate()
        );
        calendar.set(2020, Calendar.JANUARY, 5, 3, 0, 0);
        workOrder2.setCreatedAt(calendar.getTime());
        calendar.set(2020, Calendar.JANUARY, 5, 4, 30, 0);
        workOrder2.setFinishedAt(calendar.getTime()); // 1 hour and 30 minutes

        workOrderDao.save(workOrder);
        workOrderDao.save(workOrder2);

        double expected = (1 + 1.5) / 2; // 2 hour and 30 minutes / 2 = 1 hour and 15 minutes

        assertEquals(expected, workOrderDao.getAverageTimeInHoursToRepair());
    }

    @Test
    void testGetAverageTimeInHoursToRepairByTechnician(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(2020, Calendar.JANUARY, 1, 10, 0, 0);
        workOrder.setCreatedAt(calendar.getTime());
        calendar.set(2020, Calendar.JANUARY, 1, 11, 0, 0);
        workOrder.setFinishedAt(calendar.getTime()); // 1 hour

        WorkOrder workOrder2 = new WorkOrder(
                "another work order",
                IdGenerator.generate()
        );
        workOrder2.setTechnicianId(workOrder.getTechnicianId());
        calendar.set(2020, Calendar.JANUARY, 5, 3, 0, 0);
        workOrder2.setCreatedAt(calendar.getTime());
        calendar.set(2020, Calendar.JANUARY, 5, 4, 30, 0);
        workOrder2.setFinishedAt(calendar.getTime()); // 1 hour and 30 minutes

        workOrderDao.save(workOrder);
        workOrderDao.save(workOrder2);

        double expected = (1 + 1.5) / 2; // 2 hour and 30 minutes / 2 = 1 hour and 15 minutes

        assertEquals(expected, workOrderDao.getAverageTimeInHoursToRepairByTechnician(workOrder.getTechnicianId()));
    }

    @Test
    void testFetAverageWorkOrderCost(){
        workOrder.addService(new InstallationService("Paint", 100.0, 50.0));

        WorkOrder workOrder2 = new WorkOrder(
                "another work order",
                IdGenerator.generate()
        );
        workOrder2.addService(new InstallationService("Photoshop 2022", 100.0, 70.0));

        workOrderDao.save(workOrder);
        workOrderDao.save(workOrder2);

        double expected = (50 + 70.0) / 2;
        assertEquals(expected, workOrderDao.getAverageWorkOrderCost());
    }

    @Test
    void testGetAverageWorkOrderPrice(){
        workOrder.addService(new InstallationService("Windows 12", 120.0, 50.0));

        WorkOrder workOrder2 = new WorkOrder(
                "another work order",
                IdGenerator.generate()
        );
        workOrder2.addService(new InstallationService("Windows Vista", 70.54, 70.0));

        workOrderDao.save(workOrder);
        workOrderDao.save(workOrder2);

        double expected = (120 + 70.54) / 2;
        assertEquals(expected, workOrderDao.getAverageWorkOrderPrice());
    }

    @Test
    void testGetAverageCustomerSatisfaction() throws InvalidSatisfactionScoreExeption {
        workOrder.setSatisfactionScore(3);
        WorkOrder workOrder2 = new WorkOrder(
                "another work order",
                IdGenerator.generate()
        );
        workOrder2.setSatisfactionScore(5);

        workOrderDao.save(workOrder);
        workOrderDao.save(workOrder2);

        double expected = (3 + 5) / 2;
        assertEquals(expected, workOrderDao.getAverageCustomerSatifaction());
    }

}