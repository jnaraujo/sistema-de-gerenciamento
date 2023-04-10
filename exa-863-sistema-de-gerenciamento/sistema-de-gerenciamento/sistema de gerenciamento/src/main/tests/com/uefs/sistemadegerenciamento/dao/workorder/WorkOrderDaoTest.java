package com.uefs.sistemadegerenciamento.dao.workorder;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.errors.ServiceOrderWithoutTechnicianException;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
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
                UUID.randomUUID().toString(),
                "O cliente está reclamando de um problema no sistema",
                IdGenerator.generate()
        );
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
                        UUID.randomUUID().toString(),
                        "another work order",
                        IdGenerator.generate()
                ));
        orders.add(
                new WorkOrder(
                        UUID.randomUUID().toString(),
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
                UUID.randomUUID().toString(),
                "another work order",
                IdGenerator.generate()
        );

        calendar.set(2020, Calendar.JANUARY, 5);
        workOrder2.setCreatedAt(calendar.getTime());

        WorkOrder workOrder3 = new WorkOrder(
                UUID.randomUUID().toString(),
                "yet another work order",
                IdGenerator.generate()
        );
        calendar.set(2020, Calendar.JANUARY, 1);
        workOrder3.setCreatedAt(calendar.getTime());

        WorkOrder workOrder4 = new WorkOrder(
                UUID.randomUUID().toString(),
                "yet another work order",
                IdGenerator.generate()
        );
        calendar.set(2020, Calendar.JANUARY, 3);
        workOrder4.setCreatedAt(calendar.getTime());

        workOrder.setTechnicianId(IdGenerator.generate());
        workOrder.finish();

        workOrderDao.save(workOrder);
        workOrderDao.save(workOrder2);
        workOrderDao.save(workOrder3);
        workOrderDao.save(workOrder4);

        List<WorkOrder> openOrders = workOrderDao.findOpenWorkOrders();

        assertEquals(workOrder3.getId(), openOrders.get(0).getId());
        assertEquals(workOrder4.getId(), openOrders.get(1).getId());
        assertEquals(workOrder2.getId(), openOrders.get(2).getId());

    }

}