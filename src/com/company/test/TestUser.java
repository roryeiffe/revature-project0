package com.company.test;

import com.company.user.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUser {

    @Test
    public void testGet() {
        Customer customer = new Customer("Rory","password");
        assertEquals("Rory",customer.getName());
    }
}
