package com.uefs.sistemadegerenciamento.dao.customer;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoTest {
    private CustomerDao customerDao;
    private Customer customer;

    @BeforeEach
    void setUp(){
        customerDao = DAOManager.getCustomerDao();
        customer = new Customer(
                "João da Silva",
                "Rua ABC",
                "99 9 9999-9999",
                "joao@test.com"
        );
    }

    @AfterEach
    void tearDown() {
        customerDao.deleteAll();
    }

    @Test
    void testSave() {
        customer = customerDao.save(customer);
        assertEquals(customer, customerDao.findById(customer.getId()));
    }

    @Test
    void testDelete(){
        customer = customerDao.save(customer);
        assertEquals(customer, customerDao.findById(customer.getId()));

        customerDao.delete(customer.getId());
        assertNull(customerDao.findById(customer.getId()));
    }

    @Test
    void testUpdate(){
        customer = customerDao.save(customer);
        assertEquals("Rua ABC", customerDao.findById(customer.getId()).getAddress());

        customer.setAddress("Rua XYZ");
        customerDao.update(customer);

        assertEquals("Rua XYZ", customerDao.findById(customer.getId()).getAddress());
    }

    @Test
    void testGetAll(){
        customerDao.save(new Customer(
                "José da Silva",
                "Rua MNOP",
                "99 9 1111-9999",
                "jose@test.com"
        ));

        customer = customerDao.save(customer);

        assertEquals(2, customerDao.getAll().size());
        assertTrue(customerDao.getAll().contains(customer));

        Customer expectedCustomer = new Customer(
                "José da Silva",
                "Rua MNOP",
                "99 9 1111-9999",
                "jose@test.com"
        );
        expectedCustomer.setId(customer.getId());

        assertTrue(customerDao.getAll().contains(expectedCustomer));
    }
}