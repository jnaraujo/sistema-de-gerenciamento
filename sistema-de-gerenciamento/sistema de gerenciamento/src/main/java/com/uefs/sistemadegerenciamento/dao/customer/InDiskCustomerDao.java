package com.uefs.sistemadegerenciamento.dao.customer;

import com.uefs.sistemadegerenciamento.dao.FileManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *     Implementação de {@link CustomerDao} que armazena os dados em disco.
 * </p>
 *
 * @see CustomerDao
 * @author Jônatas Araújo
 */
public class InDiskCustomerDao implements CustomerDao {
    private HashMap<String, Customer> customers;
    private FileManager<String, Customer> fileManager;

    /**
     * Cria um novo {@link InDiskCustomerDao}.
     */
    public InDiskCustomerDao(String fileName){
        fileManager = new FileManager<>(fileName);
        customers = fileManager.load();
    }

    /**
     * Salva um cliente.
     * @param customer Cliente a ser salvo.
     * @return Cliente salvo com o ID gerado.
     */
    @Override
    public Customer save(Customer customer) {
        String id = IdGenerator.generate();
        customer.setId(id);

        customers.put(customer.getId(), customer);

        fileManager.save(customers);
        return customer;
    }

    /**
     * Deleta um cliente pelo ID.
     * @param customerID ID do cliente a ser deletado.
     */
    @Override
    public void delete(String customerID) {
        customers.remove(customerID);
        fileManager.save(customers);
    }

    /**
     * Atualiza um cliente.
     * @param customer Cliente a ser atualizado.
     */
    @Override
    public void update(Customer customer) {
        customers.replace(customer.getId(), customer);
        fileManager.save(customers);
    }

    /**
     * Busca um cliente pelo ID.
     * @param customerID ID do cliente a ser buscado.
     * @return Cliente encontrado.
     */
    @Override
    public Customer findById(String customerID) {
        return customers.get(customerID);
    }

    /**
     * Busca todos os clientes.
     * @return Todos os clientes.
     */
    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(customers.values());
    }

    /**
     * Deleta todos os clientes.
     */
    @Override
    public void deleteAll() {
        customers.clear();
        fileManager.save(customers);
    }
}
