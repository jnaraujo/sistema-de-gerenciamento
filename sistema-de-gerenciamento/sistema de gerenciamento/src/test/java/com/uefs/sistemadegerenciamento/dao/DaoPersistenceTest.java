package com.uefs.sistemadegerenciamento.dao;

import com.uefs.sistemadegerenciamento.dao.cleaning_service.CleaningServiceDao;
import com.uefs.sistemadegerenciamento.dao.customer.CustomerDao;
import com.uefs.sistemadegerenciamento.dao.installation_service.InstallationServiceDao;
import com.uefs.sistemadegerenciamento.dao.inventory.InventoryDao;
import com.uefs.sistemadegerenciamento.dao.user.UserDao;
import com.uefs.sistemadegerenciamento.dao.workorder.WorkOrderDao;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DaoPersistenceTest {

    private CustomerDao customerDao;
    private InventoryDao inventoryDao;
    private UserDao userDao;
    private InstallationServiceDao installationServiceDao;
    private CleaningServiceDao cleaningServiceDao;
    private WorkOrderDao workOrderDao;

    private FileManager<String, Customer> customerFileManager;
    private FileManager<String, ComputerComponent> inventoryFileManager;
    private FileManager<String, User> usersFileManager;
    private FileManager<String, InstallationService> installationServiceFileManager;
    private FileManager<String, CleaningService> cleaningServiceFileManager;
    private FileManager<String, WorkOrder> workOrderFileManager;

    @BeforeEach
    void setUp(){
        customerDao = DAOManager.getCustomerDao();
        inventoryDao = DAOManager.getInventoryDao();
        userDao = DAOManager.getUserDao();
        installationServiceDao = DAOManager.getInstallationServiceDao();
        cleaningServiceDao = DAOManager.getCleaningServiceDao();
        workOrderDao = DAOManager.getWorkOrderDao();

        customerFileManager = new FileManager<>("customers.binarydao");
        inventoryFileManager = new FileManager<>("inventory.binarydao");
        usersFileManager = new FileManager<>("users.binarydao");
        installationServiceFileManager = new FileManager<>("installationservices.binarydao");
        cleaningServiceFileManager = new FileManager<>("cleaningservices.binarydao");
        workOrderFileManager = new FileManager<>("workorders.binarydao");
    }

    @AfterEach
    void tearDown(){
        customerDao.deleteAll();
        inventoryDao.deleteAll();
        userDao.deleteAll();
        installationServiceDao.deleteAll();
        cleaningServiceDao.deleteAll();
        workOrderDao.deleteAll();
    }

    @Test
    void testSaveCustomerSave(){
        Customer customer1 = new Customer("João", "12345678910", "Rua 1", "12345678");
        Customer customer2 = new Customer("Maria", "12345678911", "Rua 2", "12345679");
        customerDao.save(customer1);
        customerDao.save(customer2);

        System.out.println(customerFileManager.load());

        assertEquals(customer1, customerFileManager.load().get(customer1.getId()));
        assertEquals(customer2, customerFileManager.load().get(customer2.getId()));
    }

    @Test
    void testSaveComputerComponentSave(){
        ComputerComponent component1 = new ComputerComponent("Placa de Vídeo", "GTX 1080", 1000.0, 10.0, 10);
        ComputerComponent component2 = new ComputerComponent("CPU", "I9", 1000.0, 10.0, 10);
        DAOManager.getInventoryDao().save(component1);
        DAOManager.getInventoryDao().save(component2);

        assertEquals(component1, inventoryFileManager.load().get(component1.getId()));
        assertEquals(component2, inventoryFileManager.load().get(component2.getId()));
    }

    @Test
    void testUserSave(){
        Technician technician1 = new Technician("Técnico 1", "12345678912", "123");
        Administrator administrator1 = new Administrator("ADM 2", "12345678913", "123");
        DAOManager.getUserDao().save(technician1);
        DAOManager.getUserDao().save(administrator1);

        assertEquals(technician1, usersFileManager.load().get(technician1.getId()));
        assertEquals(administrator1, usersFileManager.load().get(administrator1.getId()));
    }

    @Test
    void testInstallationServiceSave(){
        InstallationService installationService1 = new InstallationService("instalação de plca de video", 200.0, 100.0);
        InstallationService installationService2 = new InstallationService("instalação de cpu", 200.0, 100.0);
        DAOManager.getInstallationServiceDao().save(installationService1);
        DAOManager.getInstallationServiceDao().save(installationService2);

        assertEquals(installationService1, installationServiceFileManager.load().get(installationService1.getId()));
        assertEquals(installationService2, installationServiceFileManager.load().get(installationService2.getId()));
    }

    @Test
    void testCleaningServiceSave(){
        CleaningService cleaningService = new CleaningService(100.0, 50.0);
        cleaningService.addComponent("Placa de Vídeo");
        cleaningService.addComponent("CPU");
        DAOManager.getCleaningServiceDao().save(cleaningService);

        assertEquals(cleaningService, cleaningServiceFileManager.load().get(cleaningService.getId()));
    }


    @Test
    void testWorkOrderSave(){
        WorkOrder workOrder = new WorkOrder(
                "computador não liga",
                "12345678910"
        );
        workOrderDao.save(workOrder);

        assertEquals(workOrder, workOrderFileManager.load().get(workOrder.getId()));
    }
}
