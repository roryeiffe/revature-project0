package com.company;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {
        // Create our daos (data access objects):
        EmployeeDao employeeDao = EmployeeDaoFactory.getEmployeeDao();
        CustomerDao customerDao = CustomerDaoFactory.getCustomerDao();
        AccountDao accountDao = AccountDaoFactory.getAccountDao();
        TransactionDao transactionDao = TransactionDaoFactory.getTransactionDao();

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
            System.out.println("PRESS 13: View your accounts");
            System.out.println("PRESS 14: View a specific account");
            System.out.println("PRESS 15: Make a deposit");
            System.out.println("PRESS 16: Make a withdrawal");
            System.out.println("PRESS 17: Post a transfer");
            System.out.println("Employee Options:");
            System.out.println("PRESS 20: Login as employee");
            System.out.println("PRESS 21: Verify accounts");
            System.out.println("PRESS 22: View accounts for a specific customer");
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
                // View Accounts
                case 13:
                    if(!loggedInCustomer || customer == null) {
                        System.out.println("Must be logged in as customer to view accounts!");
                        break;
                    }
                    List<Account> accounts = accountDao.getAccountsForCustomer(customer.getId());
                    for(Account account1: accounts) {
                        System.out.println(account1.toString());
                    }
                    break;
                // View Specific Account:
                case 14:
                    if(!loggedInCustomer || customer == null) {
                        System.out.println("Must be logged in as customer to view account!");
                        break;
                    }
                    System.out.print("Please enter the id of the account which you would like to view: ");
                    id = numberReader.nextInt();
                    // fetch the account
                    account = accountDao.getAccountById(id);
                    // make sure the customer owns this account before printing:
                    if(account.getOwnerId() != customer.getId()) {
                        System.out.println("You do not own this account.");
                        break;
                    }
                    else{
                        System.out.println(account.toString());
                    }
                    break;
                // Make a deposit:
                case 15:
                    if(!loggedInCustomer || customer == null) {
                        System.out.println("Must be logged in as customer to make a deposit!");
                        break;
                    }
                    System.out.print("Enter the account which you would like to deposit: ");
                    id = numberReader.nextInt();
                    // fetch the account
                    account = accountDao.getAccountById(id);
                    if(!account.getStatus().equals("verified")){
                        System.out.println("Account not verified. Please wait for one of our employees to verify your account.");
                        break;
                    }
                    if(account.getOwnerId() != customer.getId()) {
                        System.out.println("Cannot deposit to unowned account! Must make a transfer instead.");
                        break;
                    }
                    System.out.print("Enter the amount you would like to deposit: ");
                    int money = numberReader.nextInt();
                    if (money < 0) {
                        System.out.println("You must enter a positive amount!");
                        break;
                    }
                    // update the account object
                    account.setBalance(account.getBalance() + money);
                    // update the database:
                    accountDao.update(account);
                    System.out.println("Deposit successful!");
                    break;
                // Make a withdrawal:
                case 16:
                    if(!loggedInCustomer || customer == null) {
                        System.out.println("Must be logged in as customer to make a withdrawal!");
                        break;
                    }
                    System.out.print("Enter the account which you would like to withdraw from: ");
                    id = numberReader.nextInt();
                    // fetch the account
                    account = accountDao.getAccountById(id);
                    if(!account.getStatus().equals("verified")){
                        System.out.println("Account not verified. Please wait for one of our employees to verify your account.");
                        break;
                    }
                    if(account.getOwnerId() != customer.getId()) {
                        System.out.println("Cannot withdraw from unowned account! Must make a transfer instead.");
                        break;
                    }
                    System.out.print("Enter the amount you would like to withdraw: ");
                    money = numberReader.nextInt();
                    if (money < 0) {
                        System.out.println("You must enter a positive amount!");
                        break;
                    }
                    if (account.getBalance() - money < 0) {
                        System.out.println("Not enough balance!");
                        break;
                    }
                    // update the account object
                    account.setBalance(account.getBalance() - money);
                    // update the database:
                    accountDao.update(account);
                    System.out.println("Withdraw successful!");
                    break;
                // Post a money transfer:
                case 17:
                    if(!loggedInCustomer || customer == null) {
                        System.out.println("Must be logged in as customer to post a money transfer!");
                        break;
                    }
                    System.out.print("Please enter the account id which will give the money: ");
                    int donorId = numberReader.nextInt();
                    Account accountDonor = accountDao.getAccountById(donorId);
                    if(accountDonor == null) {
                        System.out.println("Account does not exist.");
                        break;
                    }
                    if(accountDonor.getOwnerId() != customer.getId()){
                        System.out.println("You do not own this account.");
                        break;
                    }
                    if(!accountDonor.getStatus().equals("verified")){
                        System.out.println("Account not verified.");
                        break;
                    }
                    System.out.print("Please enter the account id which will receive money: ");
                    int recipId = numberReader.nextInt();
                    Account accountRecip = accountDao.getAccountById(recipId);
                    if(accountRecip == null){
                        System.out.println("Account does not exist.");
                        break;
                    }
                    if(!accountRecip.getStatus().equals("verified")){
                        System.out.println("Account not verified.");
                        break;
                    }
                    System.out.print("Please enter the amount you would like to transfer from account " + accountDonor.getId() + " to account " + accountRecip.getId() + ": ");
                    money = numberReader.nextInt();
                    if (money < 0) {
                        System.out.println("Cannot transfer a negative amount!");
                        break;
                    }
                    if (accountDonor.getBalance() - money < 0) {
                        System.out.println("Account " + accountDonor.getId() + " does not have enough money to give.");
                        break;
                    }
                    Transaction transaction = new Transaction(donorId, recipId, money);
                    transactionDao.add(transaction);
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
                // verify accounts:
                case 21:
                    if(!loggedInEmployee || employee == null) {
                        System.out.println("Must be logged in as an employee to make a verify accounts!");
                        break;
                    }
                    accounts = accountDao.getAllAccounts();
                    int count = 0;
                    for(Account account1: accounts) {
                        if (account1.getStatus().equals("unverified")){
                            System.out.println(account1.toString());
                            count ++;
                        }
                    }
                    if(count == 0) {
                        System.out.println("No unverified accounts!");
                        break;
                    }
                    System.out.println("Enter the account id you would like to verify:");
                    id = numberReader.nextInt();
                    Account account1 = accountDao.getAccountById(id);
                    account1.setStatus("verified");
                    accountDao.update(account1);
                    break;
                // View accounts for a specific customer:
                case 22:
                    if(!loggedInEmployee || employee == null) {
                        System.out.println("Must be logged in as an employee to make a view accounts!");
                        break;
                    }
                    System.out.println("Enter the customer id which you would like to view:");
                    id = numberReader.nextInt();
                    customer = customerDao.getCustomerById(id);
                    if(customer == null) {
                        System.out.println("This customer does not exist.");
                        break;
                    }
                    accounts = accountDao.getAccountsForCustomer(id);
                    count = 0;
                    for(Account account2: accounts) {
                        System.out.println(account2.toString());
                        count ++;
                    }
                    if(count == 0){
                        System.out.println("This customer has no accounts.");
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
