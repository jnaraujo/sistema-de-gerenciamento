package com.uefs.sistemadegerenciamento.dao.customer;

import com.uefs.sistemadegerenciamento.model.Customer;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *     Implementação de {@link CustomerDao} que armazena os dados em memória.
 * </p>
 *
 * @see CustomerDao
 * @author Jônatas Araújo
 */
public class InMemoryCustomerDao implements CustomerDao {
    private HashMap<String, Customer> customers;

    /**
     * Cria um novo {@link InMemoryCustomerDao}.
     */
    public InMemoryCustomerDao(){
        customers = new HashMap<>();
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

        return customer;
    }

    /**
     * Deleta um cliente pelo ID.
     * @param customerID ID do cliente a ser deletado.
     */
    @Override
    public void delete(String customerID) {
        customers.remove(customerID);
    }

    /**
     * Atualiza um cliente.
     * @param customer Cliente a ser atualizado.
     */
    @Override
    public void update(Customer customer) {
        customers.replace(customer.getId(), customer);
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
    }
}
