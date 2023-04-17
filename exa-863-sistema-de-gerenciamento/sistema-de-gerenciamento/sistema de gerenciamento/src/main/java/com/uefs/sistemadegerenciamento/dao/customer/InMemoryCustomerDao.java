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
    private HashMap<String, Customer> customers = new HashMap<>();
    @Override
    public Customer save(Customer customer) {
        String id = IdGenerator.generate();
        customer.setId(id);

        customers.put(customer.getId(), customer);

        return customer;
    }

    @Override
    public void delete(String customerID) {
        customers.remove(customerID);
    }

    @Override
    public void update(Customer customer) {
        customers.replace(customer.getId(), customer);
    }

    @Override
    public Customer findById(String customerID) {
        return customers.get(customerID);
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public void deleteAll() {
        customers.clear();
    }
}
