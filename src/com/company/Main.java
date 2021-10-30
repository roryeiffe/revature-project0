package com.company;


import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {
        // Create our daos (data access objects):
        EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();
        CustomerDao customerDao = CustomerDaoFactory.getCustomerDao();
        AccountDao accountDao = AccountDaoFactory.getAccountDao();

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
        Customer customer = null;
        boolean loggedInCustomer = false;

        boolean flag = true;
        while(flag) {
            // print out the user options, separated by user type:
            System.out.println("**********************");
            System.out.println("Customer Options:");
            System.out.println("PRESS 10: Register as Customer");
            System.out.println("PRESS 11: Login as Customer");
            System.out.println("PRESS 12: Apply for Account");
            System.out.println("Employee Options:");
            System.out.println("PRESS 20: Login as employee");
            System.out.println("PRESS 30: Exit");
            input = numberReader.nextInt();
            switch(input) {
                // Customer Register:
                case 10:
                    // enter information:
                    System.out.print("Please enter your name: ");
                    name = stringReader.nextLine();
                    System.out.print("Please enter a password: ");
                    // construct the customer object
                    password = stringReader.nextLine();
                    customer = new Customer();
                    customer.setName(name);
                    customer.setPassword(password);
                    // add customer and return whether it was successful:
                    boolean loginSuccess = customerDao.add(customer);
                    if(loginSuccess){
                        // Print a welcome message:
                        loggedInCustomer = true;
                        System.out.println("Welcome to the Munny Bank " + customer.getName() + "!");
                        System.out.println("Your id is " + customer.getId() + ", you will need this to log in!");
                    }
                    break;
                // Customer Login
                case 11:
                    // Ask for information:
                    System.out.print("Please enter your customer id: ");
                    id = numberReader.nextInt();
                    System.out.print("Please enter your password: ");
                    password = stringReader.nextLine();
                    // make the query:
                    customer = customerDao.getCustomerById(id);
                    if (customer == null) {
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
                // Apply for account
                case 12:
                    if(!loggedInCustomer || customer == null) {
                        System.out.println("Must be logged in as customer to apply for account!");
                        break;
                    }
                    System.out.print("Please enter account type (checkings, savings, etc): ");
                    String accountType = stringReader.nextLine();
                    Account account = new Account(accountType, customer.getId());
                    accountDao.add(account);
                    System.out.println("Your account id is " + account.getId());
                    break;

                // Employee Login:
                case 20:
                    // Get user information:
                    System.out.print("Please enter your employee id: ");
                    id = numberReader.nextInt();
                    System.out.print("Please enter your password: ");
                    password = stringReader.nextLine();
                    // make query to database:
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
