package com.uefs.sistemadegerenciamento.utils.converter;

import com.uefs.sistemadegerenciamento.model.Customer;
import javafx.util.StringConverter;

public class CustomerConverter extends StringConverter<Customer>{
    @Override
    public String toString(Customer customer) {
        if(customer == null) return "";

        return customer.getName() + " - " + customer.getEmail();
    }

    @Override
    public Customer fromString(String string) {
        return null;
    }
}
