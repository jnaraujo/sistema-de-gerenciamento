package com.uefs.sistemadegerenciamento.dao;

import com.uefs.sistemadegerenciamento.dao.cleaning_service.CleaningServiceDao;
import com.uefs.sistemadegerenciamento.dao.cleaning_service.InMemoryCleaningServiceDao;
import com.uefs.sistemadegerenciamento.dao.customer.CustomerDao;
import com.uefs.sistemadegerenciamento.dao.customer.InMemoryCustomerDao;
import com.uefs.sistemadegerenciamento.dao.installation_service.InMemoryInstallationServiceDao;
import com.uefs.sistemadegerenciamento.dao.installation_service.InstallationServiceDao;
import com.uefs.sistemadegerenciamento.dao.inventory.InMemoryInventoryDao;
import com.uefs.sistemadegerenciamento.dao.inventory.InventoryDao;
import com.uefs.sistemadegerenciamento.dao.technician.InMemoryTechnicianDao;
import com.uefs.sistemadegerenciamento.dao.technician.TechnicianDao;
import com.uefs.sistemadegerenciamento.dao.user.InMemoryUserDao;
import com.uefs.sistemadegerenciamento.dao.user.UserDao;
import com.uefs.sistemadegerenciamento.dao.workorder.InMemoryWorkOrderDao;
import com.uefs.sistemadegerenciamento.dao.workorder.WorkOrderDao;

/**
 * Classe que gerencia os DAOs
 */
public class DAOManager {
    private static CustomerDao customerDao;
    private static InventoryDao inventoryDao;
    private static TechnicianDao technicianDao;
    private static UserDao userDao;
    private static WorkOrderDao workOrderDao;
    private static CleaningServiceDao cleaningServiceDao;
    private static InstallationServiceDao installationServiceDao;

    /**
     * @return o CustomerDao
     */
    public static CustomerDao getCustomerDao() {
        if(customerDao == null){
            customerDao = new InMemoryCustomerDao();
        }
        return customerDao;
    }

    /**
     * @return o InventoryDao
     */
    public static InventoryDao getInventoryDao() {
        if(inventoryDao == null){
            inventoryDao = new InMemoryInventoryDao();
        }
        return inventoryDao;
    }

    /**
     * @return o TechnicianDao
     */
    public static TechnicianDao getTechnicianDao() {
        if(technicianDao == null){
            technicianDao = new InMemoryTechnicianDao();
        }
        return technicianDao;
    }

    /**
     * @return o UserDao
     */
    public static UserDao getUserDao() {
        if(userDao == null){
            userDao = new InMemoryUserDao();
        }
        return userDao;
    }

    /**
     * @return o WorkOrderDao
     */
    public static WorkOrderDao getWorkOrderDao() {
        if(workOrderDao == null){
            workOrderDao = new InMemoryWorkOrderDao();
        }
        return workOrderDao;
    }

    public static CleaningServiceDao getCleaningServiceDao(){
        if(cleaningServiceDao == null){
            cleaningServiceDao = new InMemoryCleaningServiceDao();
        }
        return cleaningServiceDao;
    }

    public static InstallationServiceDao getInstallationServiceDao(){
        if(installationServiceDao == null){
            installationServiceDao = new InMemoryInstallationServiceDao();
        }
        return installationServiceDao;
    }
}
