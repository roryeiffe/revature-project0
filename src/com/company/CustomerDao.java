package com.company;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao {
    void add(Customer customer) throws SQLException;
    void update(Customer customer);
    List<Customer> getCustomers();
    Customer getCustomerById(int id);
}
