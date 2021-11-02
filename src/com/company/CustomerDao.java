package com.company;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao {
    boolean add(User customer) throws SQLException;
    void update(Customer customer) throws SQLException;
    List<Customer> getCustomers() throws SQLException;
    Customer getCustomerById(int id) throws SQLException;
}
