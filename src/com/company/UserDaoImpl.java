package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// This dao affects both the customer and employee databases.
// To differentiate between the 2, a member variable, userType, is initialized
// to either "customer" or "employee". This variable is used to determine
// which database to query and in some other places:
public class UserDaoImpl implements UserDao{

    // what type of user dao this is:
    String userType;
    // connection to the database:
    Connection connection;

    // constructor initializes a connection and also a user type (customer vs. employee)
    public UserDaoImpl(String userType) {
        // get an instance of the connection:
        this.connection = ConnectionFactory.getConnection();
        // initialize the user type:
        this.userType = userType;
    }

    // Add a user to the appropriate database, return a boolean
    // indicating successful add:
    @Override
    public boolean add(User user) throws SQLException {
        // insert into the appropriate table:
        String sql = "insert into " + userType + " (name, password) values(?,?)";
        // prepare a statement, make sure to return the keys (id):
        PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2,user.getPassword());
        int count = preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if(count == 1) {
            System.out.println(userType + " account successfully registed!");
            resultSet.next();
            int id = resultSet.getInt(1);
            user.setId(id);
            return true;
        }
        else{
            System.out.println("Oops! Something went wrong when registering the " + userType + " account");
            return false;
        }
    }

    // Update a user entry in the database that is indicated by
    // the user object passed in
    @Override
    public void update(User user) throws SQLException {
        String sql = "Update " + userType + " set name = ?, password = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.setInt(3,user.getId());
        int count = preparedStatement.executeUpdate();
        if(count > 0) {
            System.out.println(userType + " updated.");
        }
        else{
            System.out.println("Oops! Something went wrong.");
        }
    }

    // Get a list of all users in the given database:
    @Override
    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        User user;
        String sql = "select * from " + userType;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            // create the appropriate object:
            if(userType.equals("customer")) {
                user = new Customer(id,name,password);
            }
            else{
                user = new Employee(id,name,password);
            }
            users.add(user);
        }
        return users;
    }

    // get a specific user by id:
    @Override
    public User getUserById(int id) throws SQLException {
        User user = null;
        String sql = "select * from " + userType + " where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            int id_ = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            // create the appropriate object:
            if(userType.equals("customer")) {
                user = new Customer(id_,name,password);
            }
            else{
                user = new Employee(id_,name,password);
            }
        }
        else{
            System.out.println(userType + " not found.");
        }
        return user;
    }
}
