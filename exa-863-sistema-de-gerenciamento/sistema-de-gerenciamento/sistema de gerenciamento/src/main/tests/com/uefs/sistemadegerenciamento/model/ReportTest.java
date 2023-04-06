package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {
    private static Calendar calendar;
    private List<WorkOrder> workOrders;
    private List<ComputerComponent> components;
    private Report report;

    @BeforeAll
    static void setUp() {
        calendar = Calendar.getInstance();
    }

    @BeforeEach
    void setUpEach() {
        calendar.clear();

        workOrders = new ArrayList<>();
        components = new ArrayList<>();

        // WORK ORDERS
        WorkOrder workOrder1 = new WorkOrder(
                "1",
                "O cliente está reclamando de um problema no sistema",
                IdGenerator.generate()
        );
        workOrder1.setTechnicianId(IdGenerator.generate());

        calendar.set(2020, 1, 1, 10, 30, 0);
        workOrder1.setCreatedAt(calendar.getTime());
        calendar.set(2020, 1, 1, 12, 0, 0);
        workOrder1.setFinishedAt(calendar.getTime());

        workOrder1.addService(new CleaningService(50.0, 25.0));
        workOrder1.addService(new InstallationService(100.0, 50.0));

        workOrders.add(workOrder1);


        WorkOrder workOrder2 = new WorkOrder(
                "2",
                "O cliente está reclamando de um problema no sistema",
                IdGenerator.generate()
        );
        workOrder2.setTechnicianId(IdGenerator.generate());

        calendar.set(2020, 1, 1, 10, 0, 0);
        workOrder2.setCreatedAt(calendar.getTime());
        calendar.set(2020, 1, 1, 13, 0, 0);
        workOrder2.setFinishedAt(calendar.getTime());

        BuildingService buildingService = new BuildingService();
        buildingService.addComponent(new ComputerComponent("1", "HD", "gigabyte", 250.0, 130.0, 2)); // price: 500.0,
        // cost: 250

        workOrder2.addService(buildingService);

        workOrders.add(workOrder2);

        // COMPONENTS
        ComputerComponent HDComponent = new ComputerComponent("1", "HD", "gigabyte", 100.0, 10.0, 10);
        components.add(HDComponent);

        ComputerComponent GPUComponent = new ComputerComponent("2", "gpu", "amd", 750.0, 550.0, 15);
        components.add(GPUComponent);

        ComputerComponent CPUComponent = new ComputerComponent("3", "cpu", "intel", 1000.0, 800.0, 20);
        components.add(CPUComponent);

        // REPORT
        report = new Report(workOrders, components);
    }

    @Test
    void testGetAvarageTimeToRepair(){
        assertEquals(2.25, report.getAvarageTimeInHoursToRepair());
    }

    @Test
    void testGetAvarageTimeToRepairWithNoWorkOrders(){
        List<WorkOrder> emptyWorkOrders = new ArrayList<>();
        List<ComputerComponent> emptyComponents = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> new Report(emptyWorkOrders, emptyComponents));
    }

    @Test
    void testGetAvarageTimeToRepairByTechnician(){
        assertEquals(1.5, report.getAvarageTimeInHoursToRepairByTechnician(workOrders.get(0).getTechnicianId()));
    }

    @Test
    void testGetAvarageWorkOrderCost(){
        assertEquals(167.5, report.getAvarageWorkOrderCost());
    }

    @Test
    void testGetAvarageWorkOrderPrice(){
        assertEquals(325, report.getAvarageWorkOrderPrice());
    }

    @Test
    void testGetAvarageComponentCost(){
        assertEquals(541.11, report.getAvarageComponentCost(), 0.01);
    }

    @Test
    void testGetAvarageComponentPrice(){
        assertEquals(716.66, report.getAvarageComponentPrice(), 0.01);
    }

}