package com.company;


import com.company.account.Account;
import com.company.account.AccountDao;
import com.company.account.AccountDaoFactory;
import com.company.transaction.Transaction;
import com.company.transaction.TransactionDao;
import com.company.transaction.TransactionDaoFactory;
import com.company.user.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Create our daos (data access objects):
    UserDao employeeDao;
    UserDao customerDao;
    AccountDao accountDao;
    TransactionDao transactionDao;

    // Scanners to read input
    Scanner numberReader;
    Scanner stringReader;

    // Objects to store customer and employee objects:
    User employee;
    User customer;

    // Boolean to tell whether customers and employees are logged in:
    boolean loggedInEmployee, loggedInCustomer;


    public static void main(String[] args) throws SQLException {
        Main m = new Main();
        m.mainFunction();
    }

    // initialize data that will be used throughout the program:
    public void init() {
        // DAOS:
        employeeDao = EmployeeDaoFactory.getEmployeeDao();
        customerDao = CustomerDaoFactory.getCustomerDao();
        accountDao = AccountDaoFactory.getAccountDao();
        transactionDao = TransactionDaoFactory.getTransactionDao();

        // Create scanners to read input
        numberReader = new Scanner(System.in);
        stringReader = new Scanner(System.in);

        // employee and customer objects:
        employee = null;
        customer = null;
    }

    // check if a certain type of user is logged in:
    boolean checkLoggedIn(String type, String action) {
        // check customers:
        if(type.equals("customer")) {
            // if customer is null, not logged in:
            if(customer == null) {
                System.out.println("Must be logged in as customer to " + action + ".");
                return false;
            }
            else{
                return true;
            }
        }
        // check employees:
        else if (type.equals("employee")) {
            // if employee is null, not logged in:
            if(employee == null) {
                System.out.println("Must be logged in as employee to " + action + ".");
                return false;
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }

    // Check if deposit is valid:
    boolean checkDeposit(Account account, int amount){
        System.out.println();
        if(account == null) {
            System.out.println("This account does not exist.");
            return false;
        }
        boolean valid = true;
        if(amount < 0) {
            System.out.println("Cannot deposit/withdraw negative amounts.");
            valid = false;
        }
        if(!account.getStatus().equals("verified")){
            System.out.println("Account " + account.getId() + " not verified. Please wait for one of our employees to verify your account.");
            valid = false;
        }
        if (account.getOwnerId() != customer.getId()) {
            System.out.println("You do not own this account. Must make a transfer instead.");
            valid = false;
        }
        return valid;
    }

    // check if withdrawl is valid:
    boolean checkWithdraw(Account account, int amount) {
        if(account == null) {
            return false;
        }
        boolean valid = true;
        // withdrawals must adhere to all deposit rules plus some more:
        valid = checkDeposit(account,amount);
        // make sure the user has enough balance:
        if (account.getBalance() - amount < 0) {
            // only notify of not enough balance if this is indeed our account,
            // otherwise user's could obtain information about others' accounts:
            if (account.getOwnerId() == customer.getId()) {
                System.out.println("Not enough balance.");
            }
            valid = false;
        }
        return valid;
    }

    // check if a transfer is valid, both from the giving and receiving end:
    boolean checkTransfer(Account accountDonor, Account accountRecip, String type, int amount) {
        System.out.println();
        boolean valid = true;
        if(accountDonor == null) {
            System.out.println("Donor account is not valid.");
            valid = false;
        }
        if(accountRecip == null) {
            System.out.println("Receiving account is not valid.");
            valid = false;
        }
        // if one or more accounts are null, return here so further code doesn't
        // throw exception:
        if(!valid){
            return false;
        }
        if(!accountDonor.getStatus().equals("verified")) {
            System.out.println("Donor account is not verified, please wait for an employee to verify it.");
            valid = false;
        }
        if(!accountRecip.getStatus().equals("verified")) {
            System.out.println("Receiving account is not verified, please wait for an employee to verify it.");
            valid = false;
        }
        // if we are posting this account, make sure we own the donating account
        if(type == "post" && (accountDonor.getOwnerId() != customer.getId())) {
            System.out.println("You do not own the account. You can only transfer money from an account that you own.");
            valid = false;
        }
        // if we are trying to receive but are not logged in to that account:
        if(type == "receive" && (accountRecip.getOwnerId() != customer.getId())) {
            System.out.println("You do not own this account. You can only accept money into an account that you know.");
            valid = false;
        }
        if (amount < 0) {
            System.out.println("Cannot transfer a negative amount.");
            valid = false;
        }
        // make sure the account has enough balance to give:
        if(accountDonor.getBalance() < amount) {
            System.out.println("Not enough balance for transfer.");
            valid = false;
        }
        return valid;
    }

    void printCustomerPrompts() {
        System.out.println("Customer Options:");
        System.out.println("PRESS 10: Apply for Account");
        System.out.println("PRESS 11: View your accounts");
        System.out.println("PRESS 12: View a specific account");
        System.out.println("PRESS 13: Make a deposit");
        System.out.println("PRESS 14: Make a withdrawal");
        System.out.println("PRESS 15: Post a transfer");
        System.out.println("PRESS 16: View/Accept Money Transfers");
        System.out.println("PRESS 17: Logout");
    }

    void printEmployeePrompts() {
        System.out.println("Employee Options:");
        System.out.println("PRESS 20: Verify accounts");
        System.out.println("PRESS 21: View accounts for a specific customer");
        System.out.println("PRESS 22: View a log of all transactions");
        System.out.println("PRESS 23: Logout");
    }

    void printGeneralPrompts() {
        System.out.println("General Options");
        System.out.println("PRESS 1: Register as Customer");
        System.out.println("PRESS 2: Login as Customer");
        System.out.println("PRESS 3: Login as employee");
    }

    public void mainFunction() throws SQLException {
        init();

        // Create some variables to store input
        String name;
        String password;
        int id;
        int money;
        int donorId;
        int recipId;

        // Create some placeholder objects:
        Account account;
        Account donorAccount;
        Account recipAccount;
        Transaction transaction;

        // Create some placeholder lists:
        List<Account> accounts;
        List<Transaction> transactions;

        // flag tells us when to quit the loop:
        boolean flag = true;
        // input stores which selection the user choices:
        int input;
        System.out.println("Welcome to the Munny Bank!");
        while(flag) {
            System.out.println();
            System.out.println("***********************************");
            // print out the user options, separated by user type:
            if(customer == null && employee == null) {
                printGeneralPrompts();
            }
            else if(customer != null && employee == null) {
                printCustomerPrompts();
            }
            else if(customer == null && employee != null) {
                printEmployeePrompts();
            }
            System.out.println("PRESS 4: Exit");
            System.out.println("***********************************");
            input = numberReader.nextInt();
            switch(input) {
                // Customer Register:
                case 1:
                    // enter information:
                    System.out.print("Please enter your name: ");
                    name = stringReader.nextLine();
                    System.out.print("Please enter a password: ");
                    password = stringReader.nextLine();
                    // construct the customer object
                    customer = new Customer(name,password);
                    // add customer and return whether it was successful:
                    boolean loginSuccess = customerDao.add(customer);
                    if(loginSuccess){
                        // Print a welcome message:
                        System.out.println("\nWelcome to the Munny Bank " + customer.getName() + "!");
                        System.out.println("Your id is " + customer.getId() + ", you will need this to log in!");
                        employee = null;
                    }
                    else{
                        customer = null;
                    }
                    break;
                // Customer Login
                case 2:
                    // Ask for information:
                    System.out.print("Please enter your customer id: ");
                    id = numberReader.nextInt();
                    System.out.print("Please enter your password: ");
                    password = stringReader.nextLine();
                    // make the query:
                    customer = customerDao.getUserById(id);
                    if (customer == null) {
                        System.out.println("\nCustomer with this id does not exist.");
                    }
                    else if (customer.getPassword().equals(password)) {
                        System.out.println("\nLogin successful! Welcome " + customer.getName());
                        employee = null;
                    }
                    else{
                        // if wrong password, reset customer back to null:
                        System.out.println("\nPassword incorrect");
                        loggedInCustomer = false;
                        customer = null;
                    }
                    break;
                // Apply for account
                case 10:
                    if(!checkLoggedIn("customer","apply for account")) {
                        break;
                    }
                    // Prompt the user for account information:
                    System.out.print("Please enter account type (checkings, savings, etc): ");
                    String accountName = stringReader.nextLine();
                    System.out.print("Please enter your starting balance: ");
                    int amount = numberReader.nextInt();
                    account = new Account(accountName, customer.getId(),amount);
                    // Add the account to the database and return the new id:
                    accountDao.add(account);
                    System.out.println("\nYour account id is " + account.getId());
                    System.out.println("Please wait for your account to be verified.");
                    break;
                // View Accounts
                case 11:
                    if(!checkLoggedIn("customer","view accounts")) {
                        break;
                    }
                    // fetch all accounts for this customer:
                    accounts = accountDao.getAccountsForCustomer(customer.getId());
                    if(accounts.size() == 0){
                        System.out.println("You have no current accounts.");
                    }
                    else{
                        System.out.println("Here are your accounts:");
                    }
                    // print out the customer's accounts:
                    for(Account account1: accounts) {
                        account1.print();
                    }
                    break;
                // View Specific Account:
                case 12:
                    if(!checkLoggedIn("customer","view account")) {
                        break;
                    }
                    System.out.print("Please enter the id of the account which you would like to view: ");
                    id = numberReader.nextInt();
                    // fetch the account
                    account = accountDao.getAccountById(id);
                    if(account == null) {
                        System.out.println("\nAccount does not exist.");
                        break;
                    }
                    // make sure the customer owns this account before printing:
                    if(account.getOwnerId() != customer.getId()) {
                        System.out.println("\nYou do not own this account.");
                        break;
                    }
                    else{
                        System.out.println();
                        account.print();
                    }
                    break;
                // Make a deposit:
                case 13:
                    if(!checkLoggedIn("customer","make a deposit")) {
                        break;
                    }
                    // Read input and fetch account from database:
                    System.out.print("Enter the account which you would like to deposit: ");
                    id = numberReader.nextInt();
                    System.out.print("Enter the amount you would like to deposit: ");
                    money = numberReader.nextInt();
                    account = accountDao.getAccountById(id);
                    // make sure the deposit os valid:
                    if(checkDeposit(account,money)) {
                        // update the account object
                        account.setBalance(account.getBalance() + money);
                        // update the database:
                        accountDao.update(account);
                        System.out.println("\nDeposit successful!");
                    }
                    break;
                // Make a withdrawal:
                case 14:
                    if(!checkLoggedIn("customer","withdraw from an account")) {
                        break;
                    }
                    // get input and fetch account from database:
                    System.out.print("Enter the account which you would like to withdraw from: ");
                    id = numberReader.nextInt();
                    System.out.print("Enter the amount you would like to withdraw: ");
                    money = numberReader.nextInt();
                    account = accountDao.getAccountById(id);
                    if(checkWithdraw(account,money)) {
                        // update the account object
                        account.setBalance(account.getBalance() - money);
                        // update the database:
                        accountDao.update(account);
                        System.out.println("Withdraw successful!");
                    }
                    break;
                // Post a money transfer:
                case 15:
                    if(!checkLoggedIn("customer","post a money transfer")) {
                        break;
                    }
                    // get input from the user and fetch accounts from database:
                    System.out.print("Please enter the account id which will give the money: ");
                    donorId = numberReader.nextInt();
                    System.out.print("Please enter the account id which will receive money: ");
                    recipId = numberReader.nextInt();
                    System.out.print("Please enter the amount you would like to transfer: ");
                    money = numberReader.nextInt();
                    Account accountDonor = accountDao.getAccountById(donorId);
                    Account accountRecip = accountDao.getAccountById(recipId);

                    // check that transfer is valid:
                    if(checkTransfer(accountDonor, accountRecip,"post",money)){
                        transaction = new Transaction(donorId, recipId, money);
                        transactionDao.add(transaction);
                    }
                    break;
                // Accept money transfer:
                case 16:
                    if(!checkLoggedIn("customer","accept a money transfer")) {
                        break;
                    }
                    accounts = accountDao.getAccountsForCustomer(customer.getId());
                    int count = 0;
                    for(Account account1:accounts) {
                        transactions = transactionDao.getAllIncoming(account1.getId());
                        for(Transaction transaction1: transactions) {
                            // only accept non-accepted transactions:
                            if(transaction1.getStatus().equals("pending")) {
                                System.out.println("Incoming transaction (id: " + transaction1.getId() + ") from account " + transaction1.getDonor_id() + " for " + transaction1.getAmount() + " dollars.");
                                count ++;
                            }
                        }
                    }
                    if(count == 0){
                        System.out.println("\nNo incoming transactions.");
                        break;
                    }
                    // Get input and retrieve info from the database:
                    System.out.println("Which transaction would you like to accept?");
                    id = numberReader.nextInt();
                    transaction = transactionDao.get(id);
                    money = transaction.getAmount();
                    donorAccount = accountDao.getAccountById(transaction.getDonor_id());
                    recipAccount = accountDao.getAccountById(transaction.getRecip_id());
                    // check if transfer is still valid:
                    if(!checkTransfer(donorAccount,recipAccount,"receive",money)) {
                        // transaction failed:
                        System.out.println("Transaction cancelled.");
                        transaction.setStatus("cancelled");
                        transactionDao.update(transaction);
                        break;
                    }
                    // transaction success:
                    else{
                        // update both accounts:
                        donorAccount.setBalance(donorAccount.getBalance() - money);
                        recipAccount.setBalance(recipAccount.getBalance() + money);
                        // update the transaction:
                        transaction.setStatus("accepted");
                        // update the databases:
                        accountDao.update(donorAccount);
                        accountDao.update(recipAccount);
                        transactionDao.update(transaction);
                    }

                    break;
                case 17:
                    customer = null;
                    System.out.println("\nThank you for using the Munny Bank! Logging out!");
                    break;
                // Employee Login:
                case 3:
                    // Get user information:
                    System.out.print("Please enter your employee id: ");
                    id = numberReader.nextInt();
                    System.out.print("Please enter your password: ");
                    password = stringReader.nextLine();
                    // make query to database:
                    employee = employeeDao.getUserById(id);
                    if (employee == null) {
                        System.out.println("Id does not exist.");
                    }
                    else if (employee.getPassword().equals(password)) {
                        System.out.println("Login successful! Welcome " + employee.getName());
                        customer = null;
                    }
                    else{
                        // if wrong password, reset employee back to null:
                        System.out.println("Password incorrect");
                        employee = null;
                    }
                    customer = null;
                    break;
                // verify accounts:
                case 20:
                    if(!checkLoggedIn("employee","verify accounts")) {
                        break;
                    }
                    accounts = accountDao.getAllAccounts();
                    // print out all unverified accounts:
                    count = 0;
                    for(Account account1: accounts) {
                        if (account1.getStatus().equals("unverified")){
                            account1.printUnverified();
                            count ++;
                        }
                    }
                    if(count == 0) {
                        System.out.println("\nNo unverified accounts!");
                        break;
                    }
                    // take user input:
                    System.out.print("\nEnter the account id you would like to verify: ");
                    id = numberReader.nextInt();
                    account = accountDao.getAccountById(id);
                    if (account != null && !account.getStatus().equals("verified")){
                        account.setStatus("verified");
                        accountDao.update(account);
                        System.out.println("\nAccount verified!");
                    }
                    break;
                // View accounts for a specific customer:
                case 21:
                    if(!checkLoggedIn("employee","view accounts")) {
                        break;
                    }
                    // get input:
                    System.out.println("Enter the customer id which you would like to view:");
                    id = numberReader.nextInt();
                    customer = customerDao.getUserById(id);
                    if(customer == null) {
                        System.out.println("\nThis customer does not exist.");
                        break;
                    }
                    accounts = accountDao.getAccountsForCustomer(id);
                    count = 0;
                    System.out.println();
                    for(Account account1: accounts) {
                        account1.print();
                        count ++;
                    }
                    if(count == 0){
                        System.out.println("\nThis customer has no accounts.");
                    }
                    customer = null;
                    break;
                // Exiting the program:
                case 22:
                    if(!checkLoggedIn("employee","view transactions")) {
                        break;
                    }
                    transactions = transactionDao.getAll();
                    for(Transaction transaction1:transactions) {
                        transaction1.print();
                    }
                    break;
                case 23:
                    employee = null;
                    System.out.println("\nThank you for using the Munny Bank! Logging out!");
                    break;
                case 4:
                    System.out.println("Thank you for using the munny bank!");
                    flag = false;
                    break;
                default:
                    System.out.println("\nSorry, please enter a valid command.");
            }
        }
    }




}
