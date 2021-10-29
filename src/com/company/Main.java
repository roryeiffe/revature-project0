package com.company;


import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {
        // Create our daos (data access objects):
        EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();

        // Create scanners to read input
        Scanner numberReader = new Scanner(System.in);
        Scanner stringReader = new Scanner(System.in);
        int input;

        // Employee variables, these will start uninitialized
        Employee employee = null;
        String name;
        String password;
        boolean loggedInEmployee = false;
        int id;

        boolean flag = true;
        while(flag) {
            System.out.println("**********************");
            System.out.println("Customer Options");
            System.out.println("PRESS 10: Register as Customer");
            System.out.println("Employee Options");
            System.out.println("PRESS 20: Login as employee");
            System.out.println("PRESS 30: Exit");
            input = numberReader.nextInt();
            switch(input) {
                case 10:
                    System.out.print("Please enter your name: ");
                    name =
                case 20:
                    System.out.print("Please enter your employeeid: ");
                    id = numberReader.nextInt();
                    System.out.print("Please enter your password: ");
                    password = stringReader.nextLine();
                    employee = employeeDao.getEmployeeById(id);
                    if (employee == null) {
                        System.out.println("Id does not exist.");
                    }
                    else if (employee.getPassword().equals(password)) {
                        System.out.println("Login successful! Welcome " + employee.getName());
                        loggedInEmployee = true;
                    }
                    else{
                        // if wrong password, reset employee back to null:
                        System.out.println("Password incorrect");
                        employee = null;
                    }
                case 30:
                    System.out.println("Thank you for using the munny bank!");
                    flag = false;
            }
        }
    }


}
