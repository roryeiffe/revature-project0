package com.company;


import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {
        // Create our daos (data access objects):
        EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();
        CustomerDao customerDao = CustomerDaoFactory.getCustomerDao();

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

        // Customer variables:
        Customer customer;
        boolean loggedInCustomer = false;

        boolean flag = true;
        while(flag) {
            System.out.println("**********************");
            System.out.println("Customer Options:");
            System.out.println("PRESS 10: Register as Customer");
            System.out.println("PRESS 11: Login as Customer");
            System.out.println("Employee Options:");
            System.out.println("PRESS 20: Login as employee");
            System.out.println("PRESS 30: Exit");
            input = numberReader.nextInt();
            switch(input) {
                // Customer Register:
                case 10:
                    System.out.print("Please enter your name: ");
                    name = stringReader.nextLine();
                    System.out.print("Please enter a password: ");
                    password = stringReader.nextLine();
                    customer = new Customer();
                    customer.setName(name);
                    customer.setPassword(password);
                    // add customer and return whether it was successful:
                    boolean loginSuccess = customerDao.add(customer);
                    if(loginSuccess){
                        loggedInCustomer = true;
                        System.out.println("Welcome to the Munny Bank " + customer.getName() + "!");
                        System.out.println("Your id is " + customer.getId() + ", you will need this to log in!");
                    }
                    break;
                // Customer Login
                case 11:
                    System.out.print("Please enter your customer id: ");
                    id = numberReader.nextInt();
                    System.out.print("Please enter your password: ");
                    password = stringReader.nextLine();
                    customer = customerDao.getCustomerById(id);
                    if (customer== null) {
                        System.out.println("Id does not exist.");
                    }
                    else if (customer.getPassword().equals(password)) {
                        System.out.println("Login successful! Welcome " + customer.getName());
                        loggedInCustomer = true;
                    }
                    else{
                        // if wrong password, reset customer back to null:
                        System.out.println("Password incorrect");
                        customer = null;
                    }
                    break;
                // Employee Login:
                case 20:
                    System.out.print("Please enter your employee id: ");
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
                    break;
                // Exiting the program:
                case 30:
                    System.out.println("Thank you for using the munny bank!");
                    flag = false;
                    break;
            }
        }
    }


}
