package com.company.test;

import com.company.user.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUser {

    // ensure that customer creation and get methods are working
    @Test
    public void testGet() {
        Customer customer = new Customer("Rory","password");
        assertEquals("Rory",customer.getName());
    }
}
