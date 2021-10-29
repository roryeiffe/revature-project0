package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{

    Connection connection;
    public CustomerDaoImpl() {
        // get an instance of the connection:
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void add(Customer customer) throws SQLException {
        String sql = "insert into customers (name, password) values(?,?)";
        // prepare a statement, make sure to return the keys (id):
        PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,customer.getName());
        preparedStatement.setString(2,customer.getPassword());
        int count = preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if(count == 1) {
            System.out.println("Customer account successfully registed!");
            resultSet.next();
            int id = resultSet.getInt(1);
            customer.setId(id);
        }
        else{
            System.out.println("Oops! Something went wrong when registering the customer account");
        }
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public List<Customer> getCustomers() {
        return null;
    }

    @Override
    public Customer getCustomerById(int id) {
        return null;
    }
}
