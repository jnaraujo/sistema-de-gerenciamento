package com.uefs.sistemadegerenciamento.utils;

import com.uefs.sistemadegerenciamento.constants.OrderStatus;
import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.errors.InvalidSatisfactionScoreException;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.model.WorkOrder;
import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.model.user.Receptionist;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DaoMock {

    public void mock() {
        List<User> users = generateUsers();
        List<Customer> customers = generateCustomers();
        List<CleaningService> cleaningServices = generateCleaningService();
        List<InstallationService> installationServices = generateInstallationService();
        List<ComputerComponent> components = generateComponents();

        // Salvando componentes
        components.forEach(DAOManager.getInventoryDao()::save);

        // Salvando serviços de limpeza
        cleaningServices.forEach(DAOManager.getCleaningServiceDao()::save);

        // Salvando serviços de instalação
        installationServices.forEach(DAOManager.getInstallationServiceDao()::save);

        // Salvando usuários e clientes
        users = users.stream().filter((user) -> DAOManager.getUserDao().findByEmail(user.getEmail()) == null).toList();
        users.forEach(DAOManager.getUserDao()::save);

        customers = customers.stream().filter((customer) -> DAOManager.getCustomerDao().getAll().stream().noneMatch((c) -> c.getName().equals(customer.getName()))).toList();
        customers.forEach(DAOManager.getCustomerDao()::save);

        // Buscando tecnicos
        List<Technician> technicians = DAOManager.getUserDao().findAllTechnicians();

        // Gerando ordens de serviço
        List<WorkOrder> workOrders = generateWorkOrders(customers, technicians);

        // Adicionando serviços às ordens de serviço
        if(workOrders.size() >= 3){
            workOrders.get(0).addService(installationServices.get(0));
            workOrders.get(0).addService(cleaningServices.get(0));
            workOrders.get(1).addService(installationServices.get(1));
            workOrders.get(1).addService(cleaningServices.get(1));
            workOrders.get(2).addService(installationServices.get(2));
            workOrders.get(2).addService(cleaningServices.get(2));
        }

        // Salvando ordens de serviço
        workOrders.forEach(DAOManager.getWorkOrderDao()::save);
    }

    private List<ComputerComponent> generateComponents(){
        List<ComputerComponent> components = new ArrayList<>();

        components.add(new ComputerComponent("RX 580", "AMD", 500.00, 250.00, 15));
        components.add(new ComputerComponent("GTX 1050", "NVIDIA", 750.00, 550.00, 5));
        components.add(new ComputerComponent("GTX 1060", "NVIDIA", 800.00, 360.00, 3));
        components.add(new ComputerComponent("Ryzen 5 3600", "AMD", 1000.00, 500.00, 10));
        components.add(new ComputerComponent("Ryzen 7 3700", "AMD", 1500.00, 750.00, 5));

        return components;
    }

    private List<InstallationService> generateInstallationService(){
        List<InstallationService> services = new ArrayList<>();

        services.add(new InstallationService("Windows", 100, 50));
        services.add(new InstallationService("Linux", 100, 50));
        services.add(new InstallationService("MacOS", 100, 50));
        services.add(new InstallationService("Drivers", 100, 50));

        return services;
    }

    private List<CleaningService> generateCleaningService(){
        CleaningService service1 = new CleaningService(500, 250);
        service1.addComponent("Placa de vídeo");

        CleaningService service2 = new CleaningService(100, 50);
        service2.addComponent("Placa Mãe");

        CleaningService service3 = new CleaningService(200, 100);
        service3.addComponent("Completa");

        return List.of(service1, service2, service3);
    }

    private List<User> generateUsers() {
        List<User> users = new ArrayList<>();

        users.add(new Receptionist("Rodrigo", "rodrigo", "rodrigo"));
        users.add(new Receptionist("Pedro", "pedro", "pedro"));
        users.add(new Receptionist("João", "joao", "joao"));

        users.add(new Technician("Steve", "steve", "steve"));
        users.add(new Technician("Bill", "bill", "bill"));
        users.add(new Technician("Linus", "linus", "linus"));

        return users;
    }

    private List<Customer> generateCustomers() {
        List<Customer> customers = new ArrayList<>();

        customers.add(new Customer("Luiz", "Rua A", "99 9 9999-9999", "luiz@teste.com"));
        customers.add(new Customer("Maria", "Rua B", "99 9 9999-9999", "maria@teste.com"));
        customers.add(new Customer("José", "Rua C", "99 9 9999-9999", "jose@teste.com"));

        return customers;
    }

    private List<WorkOrder> generateWorkOrders(List<Customer> customers, List<Technician> technicians){
        List<WorkOrder> workOrders = new ArrayList<>();

        if(customers.size() < 2 || technicians.size() < 2){
            return List.of();
        }

        for (int i = 0; i < customers.size(); i++){
            Customer customer = customers.get(i);
            Technician technician = technicians.get(i);

            WorkOrder workOrder = new WorkOrder("Descrição da ordem de serviço", customer.getId());
            workOrder.setTechnicianId(technician.getId());

            workOrders.add(workOrder);
        }

        // Adicionando uma ordem de serviço sem técnico
        workOrders.add(new WorkOrder("Descrição da ordem de serviço", customers.get(0).getId()));
        workOrders.add(new WorkOrder("Descrição da ordem de serviço", customers.get(1).getId()));

        // Setando datas para simular ordens de serviço abertas e fechadas
        // Primeira ordem de serviço
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 6, 1, 0, 0);
        workOrders.get(0).setCreatedAt(calendar.getTime());

        calendar.set(2023, 6, 2, 0, 0);
        workOrders.get(0).setFinishedAt(calendar.getTime());
        workOrders.get(0).setStatus(OrderStatus.CLOSED);
        workOrders.get(0).setPaymentMethod("Dinheiro");
        try {
            workOrders.get(0).setSatisfactionScore(5);
        } catch (InvalidSatisfactionScoreException e) {
            throw new RuntimeException(e);
        }

        // Segunda ordem de serviço
        calendar.set(2023, 6, 5, 0, 0);
        workOrders.get(1).setCreatedAt(calendar.getTime());

        calendar.set(2023, 6, 12, 0, 0);
        workOrders.get(1).setFinishedAt(calendar.getTime());
        workOrders.get(1).setStatus(OrderStatus.CLOSED);
        workOrders.get(1).setPaymentMethod("Cartão");
        try {
            workOrders.get(1).setSatisfactionScore(4);
        } catch (InvalidSatisfactionScoreException e) {
            throw new RuntimeException(e);
        }

        return workOrders;
    }
}
