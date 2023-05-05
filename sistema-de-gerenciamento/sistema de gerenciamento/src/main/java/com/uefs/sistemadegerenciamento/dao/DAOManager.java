package com.uefs.sistemadegerenciamento.dao;

import com.uefs.sistemadegerenciamento.dao.cleaning_service.CleaningServiceDao;
import com.uefs.sistemadegerenciamento.dao.cleaning_service.DiskCleaningServiceDao;
import com.uefs.sistemadegerenciamento.dao.customer.CustomerDao;
import com.uefs.sistemadegerenciamento.dao.customer.DiskCustomerDao;
import com.uefs.sistemadegerenciamento.dao.customer.InMemoryCustomerDao;
import com.uefs.sistemadegerenciamento.dao.installation_service.DiskInstallationServiceDao;
import com.uefs.sistemadegerenciamento.dao.installation_service.InstallationServiceDao;
import com.uefs.sistemadegerenciamento.dao.inventory.DiskInventoryDao;
import com.uefs.sistemadegerenciamento.dao.inventory.InventoryDao;
import com.uefs.sistemadegerenciamento.dao.user.DiskUserDao;
import com.uefs.sistemadegerenciamento.dao.user.UserDao;
import com.uefs.sistemadegerenciamento.dao.workorder.DiskWorkOrderDao;
import com.uefs.sistemadegerenciamento.dao.workorder.WorkOrderDao;

/**
 * <p>
 * Classe que gerencia os DAOs do sistema
 * Oferece métodos estáticos para obter os DAOs
 * </p>
 *
 * <p>
 * Se não houve nenhum DAO criado, cria um DAO em memória
 * se já houve um DAO criado, retorna o mesmo
 * </p>
 *
 * @see CustomerDao
 * @see InventoryDao
 * @see UserDao
 * @see WorkOrderDao
 * @see CleaningServiceDao
 * @see InstallationServiceDao
 * @see InMemoryCustomerDao
 * @author Jônatas Araújo
 */
public class DAOManager {
    private static CustomerDao customerDao;
    private static InventoryDao inventoryDao;
    private static UserDao userDao;
    private static WorkOrderDao workOrderDao;
    private static CleaningServiceDao cleaningServiceDao;
    private static InstallationServiceDao installationServiceDao;

    /**
     * Retorna o DAO para gerenciamento de clientes.
     * @return o CustomerDao
     */
    public static CustomerDao getCustomerDao() {
        if(customerDao == null){
            customerDao = new DiskCustomerDao("customers.binarydao");
        }
        return customerDao;
    }

    /**
     * Retorna o DAO para gerenciamento de inventário.
     * @return o InventoryDao
     */
    public static InventoryDao getInventoryDao() {
        if(inventoryDao == null){
            inventoryDao = new DiskInventoryDao("inventory.binarydao");
        }
        return inventoryDao;
    }
    /**
     * Retorna o DAO para gerenciamento de usuários.
     * @return o UserDao
     */
    public static UserDao getUserDao() {
        if(userDao == null){
            userDao = new DiskUserDao("users.binarydao");
        }
        return userDao;
    }

    /**
     * Retorna o DAO para gerenciamento de ordens de serviço.
     * @return o WorkOrderDao
     */
    public static WorkOrderDao getWorkOrderDao() {
        if(workOrderDao == null){
            workOrderDao = new DiskWorkOrderDao("workorders.binarydao");
        }
        return workOrderDao;
    }

    /**
     * Retorna o DAO para gerenciamento de serviços de limpeza.
     * @return o CleaningServiceDao
     */
    public static CleaningServiceDao getCleaningServiceDao(){
        if(cleaningServiceDao == null){
            cleaningServiceDao = new DiskCleaningServiceDao("cleaningservices.binarydao");
        }
        return cleaningServiceDao;
    }

    /**
     * Retorna o DAO para gerenciamento de serviços de instalação.
     * @return o InstallationServiceDao
     */
    public static InstallationServiceDao getInstallationServiceDao(){
        if(installationServiceDao == null){
            installationServiceDao = new DiskInstallationServiceDao("installationservices.binarydao");
        }
        return installationServiceDao;
    }
}
