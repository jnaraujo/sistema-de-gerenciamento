package com.uefs.sistemadegerenciamento.utils.converter;

import com.uefs.sistemadegerenciamento.model.Customer;
import javafx.util.StringConverter;

import java.util.List;

public class CustomerConverter extends StringConverter<Customer>{
    List<Customer> customers;

    public CustomerConverter(List<Customer> customers){
        this.customers = customers;
    }

    @Override
    public String toString(Customer customer) {
        if(customer == null) return "";

        return customer.getName() + " - " + customer.getEmail();
    }

    @Override
    public Customer fromString(String string) {
        return (Customer) customers
                .stream()
                .filter(customer -> this.toString((Customer) customer).equals(string))
                .findFirst()
                .orElse(null);
    }
}
