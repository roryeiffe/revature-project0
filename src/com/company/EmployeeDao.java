package com.company;

import java.sql.SQLException;
import java.util.List;

// this methods relate to the employee and the employee database:
public interface EmployeeDao {
    void addEmployee(Employee employee) throws SQLException;
    void updateEmployee(Employee employee) throws SQLException;
    List<Employee> getEmployees() throws SQLException;
    Employee getEmployeeById(int id) throws SQLException;
}
