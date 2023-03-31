package com.uefs.sistemadegerenciamento.dao.customer;

import com.uefs.sistemadegerenciamento.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryCustomerDao implements CustomerDao {
    private HashMap<String, Customer> customers = new HashMap<>();
    @Override
    public void save(Customer customer) {
        customers.put(customer.getId(), customer);
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
}
