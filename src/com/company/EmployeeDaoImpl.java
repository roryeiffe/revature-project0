package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// this method is the implementation of the employee doa methods:
public class EmployeeDaoImpl implements EmployeeDao{

    // establish a connection with the database:
    Connection connection;

    public EmployeeDaoImpl() {
        this.connection = ConnectionFactory.getConnection();
    }

    // Given a name and a password, insert these values into the database:
    @Override
    public void addEmployee(Employee employee) throws SQLException {
        String sql = "insert into employee (name, password) values (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,employee.getName());
        preparedStatement.setString(2,employee.getPassword());
        // execute the update:
        int count = preparedStatement.executeUpdate();
        // get the id back:
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while(resultSet.next()) {
            // get id of the result set:
            int id = resultSet.getInt(1);
            // update the employee object with the id:
            employee.setId(id);
        }
        if (count > 0) {
            System.out.println("Employee saved.");
        }
        else{
            System.out.println("Oops! Something went wrong.");
        }
    }

    // given an employee object, update the database:
    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        String sql = "update employee set name = ?, password = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,employee.getName());
        preparedStatement.setString(2,employee.getPassword());
        preparedStatement.setInt(3,employee.getId());
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Employee updated.");
        }
        else{
            System.out.println("Oops! Something went wrong.");
        }
    }

    @Override
    public List<Employee> getEmployees() throws SQLException {
        // make a list of employees:
        List<Employee> employees = new ArrayList<>();
        // create query statement:
        String sql = "Select * from employee;";
        // prepare a statement:
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // executre the query:
        ResultSet resultSet = preparedStatement.executeQuery();
        // loop through the returned rows:
        while(resultSet.next()){
            // extract the data:
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String password = resultSet.getString(3);
            // create a new employee and add it to the list:
            Employee emp = new Employee(id,name,password);
            employees.add(emp);
        }
        // return all employees:
        return employees;
    }

    @Override
    public Employee getEmployeeById(int id) throws SQLException {
        Employee emp = null;
        String sql = "select * from employee where id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id_ = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            emp = new Employee(id_,name,email);
        }
        else{
            System.out.println("no records found");
        }
        return emp;
    }
}
