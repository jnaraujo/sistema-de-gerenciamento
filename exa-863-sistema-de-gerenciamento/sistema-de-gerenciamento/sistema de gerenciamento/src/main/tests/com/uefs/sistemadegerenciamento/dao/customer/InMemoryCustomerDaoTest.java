package com.uefs.sistemadegerenciamento.dao.customer;

import com.uefs.sistemadegerenciamento.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryCustomerDaoTest {
    private InMemoryCustomerDao customerDao;
    private Customer customer;

    @BeforeEach
    void setUp(){
        customerDao = new InMemoryCustomerDao();
        customer = new Customer(
                UUID.randomUUID().toString(),
                "João da Silva",
                "Rua ABC",
                "99 9 9999-9999",
                "joao@test.com"
        );
    }

    @Test
    void testSave() {
        customerDao.save(customer);
        assertEquals(customer, customerDao.findById(customer.getId()));
    }

    @Test
    void testDelete(){
        customerDao.save(customer);
        assertEquals(customer, customerDao.findById(customer.getId()));
    }

    @Test
    void testUpdate(){
        customerDao.save(customer);
        assertEquals("Rua ABC", customerDao.findById(customer.getId()).getAddress());

        customer.setAddress("Rua XYZ");
        customerDao.update(customer);

        assertEquals("Rua XYZ", customerDao.findById(customer.getId()).getAddress());
    }

    @Test
    void testGetAll(){
        String id = UUID.randomUUID().toString();

        customerDao.save(new Customer(
                id,
                "José da Silva",
                "Rua MNOP",
                "99 9 1111-9999",
                "jose@test.com"
        ));
        customerDao.save(customer);

        assertEquals(2, customerDao.getAll().size());
        assertTrue(customerDao.getAll().contains(customer));
        assertTrue(customerDao.getAll().contains(new Customer(
                id,
                "José da Silva",
                "Rua MNOP",
                "99 9 1111-9999",
                "jose@test.com"
        )));
    }
}