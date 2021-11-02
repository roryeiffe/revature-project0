package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Respsonible for accessing/updating the database
public class CustomerDaoImpl implements CustomerDao{

    Connection connection;
    public CustomerDaoImpl() {
        // get an instance of the connection:
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public boolean add(User customer) throws SQLException {
        String sql = "insert into customer (name, password) values(?,?)";
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
            return true;
        }
        else{
            System.out.println("Oops! Something went wrong when registering the customer account");
            return false;
        }
    }

    // update a given customer in the database:
    @Override
    public void update(Customer customer) throws SQLException {
        String sql = "Update customer set name = ?, password = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,customer.getName());
        preparedStatement.setString(2,customer.getPassword());
        preparedStatement.setInt(3,customer.getId());
        int count = preparedStatement.executeUpdate();
        if(count > 0) {
            System.out.println("Customer updated.");
        }
        else{
            System.out.println("Oops! Something went wrong.");
        }

    }

    // get all customers:
    @Override
    public List<Customer> getCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<Customer>();
        String sql = "select * from customer";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            Customer customer = new Customer(id,name,password);
            customers.add(customer);
        }
        return customers;
    }

    // given an id, return the customer:
    @Override
    public Customer getCustomerById(int id_) throws SQLException {
        Customer customer = null;
        String sql = "select * from customer where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id_);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            customer = new Customer(id,name,password);
        }
        else{
            System.out.println("Customer not found.");
        }
        return customer;
    }
}
