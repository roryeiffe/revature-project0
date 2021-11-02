//package com.company;
//
//import java.util.Scanner;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//class CommandLine {
//
//    // these instance variables store information about the different
//    // user types:
//    Customer customer;
//    Employee employee;
//
//    // scanners will be used to collect user input:
//    Scanner scanner;
//    Scanner scannerInt;
//
//
//    // this method runs the command line:
//    public void run() {
//        scanner = new Scanner(System.in);
//        scannerInt = new Scanner(System.in);
//        // set up account objects:
//        customer = new Customer();
//        employee = new Employee();
//        System.out.println("Welcome to Munny Bank! Type help for commands!");
//        // accept input during this while loop:
//        while(true) {
//            System.out.print("Enter your command: ");
//            String input = scanner.nextLine();
//            String[] inputs = input.split(" ");
//            if (inputs[0].equals("help")) {
//                // if logged in as customer, print commands
//                // that a customer can execute:
//                if (customer.loggedIn){
//                    print_commands_customer();
//                }
//                else{
//                    print_commands_general();
//                }
//            }
//            // login/register for accounts:
//            else if(inputs[0].equals("register")) {
//                auth(inputs[1],"register");
//            }
//            else if(inputs[0].equals("login")) {
//                auth(inputs[1],"login");
//            }
//            // commands that customers can access:
//            else if(inputs[0].equals("apply") && customer.loggedIn) {
//                apply();
//            }
//            else if(inputs[0].equals("view") && customer.loggedIn) {
//
//            }
//            else if(inputs[0].equals("withdraw") && customer.loggedIn) {
//                withdraw();
//            }
//            else if(inputs[0].equals("deposit") && customer.loggedIn) {
//
//            }
//            else if(inputs[0].equals("transfer") && customer.loggedIn) {
//
//            }
//        }
//    }
//
//    // print out the different commands:
//    public static void print_commands_general() {
//        System.out.println("Type 'login <user_type>' to log in.");
//        System.out.println("Type 'register <user_type>' to register.");
//        System.out.println();
//    }
//
//    public static void print_commands_customer() {
//        System.out.println("Type 'apply' to apply for a new bank account.");
//        System.out.println("Type 'view balance' to view the balance of one of your bank accounts.");
//        System.out.println("Type 'withdraw' to make withdraw from one of your accounts.");
//        System.out.println("Type 'deposit' to make a deposit to one of your accounts.");
//        System.out.println("Type 'transfer' to transfer money to an account.");
//        System.out.println("Type 'view transfers' to view/accept incoming transfers.");
//    }
//
//    public void auth(String accountType, String authType) {
//        // error checking:
//        // make sure it is a valid account type:
//        if (!(accountType.equals("customer") || accountType.equals("employee"))){
//            System.out.println("Error: account type must be 'customer' or 'employee'");
//            return;
//        }
//        // make sure user is not already logged in:
//        if( (accountType.equals("customer") && customer.loggedIn) || accountType.equals("employee") && employee.loggedIn){
//            System.out.println("You are already logged in!");
//            return;
//        }
//        // route to the appropriate authorization function:
//        if (authType.equals("register")) {
//            register(accountType);
//        }
//        else if (authType.equals("login")) {
//            logIn(accountType);
//        }
//    }
//
//    // let the user login to a particular account:
//    public void register(String accountType) {
//        // take the inputs:
//        System.out.print("Please enter your name: ");
//        String name = scanner.nextLine();
//        System.out.print("Please enter a password: ");
//        String password = scanner.nextLine();
//        // if customer account, use the customer class:
//        if(accountType.equals("customer")) {
//            customer.register(name,password);
//        }
//        else if (accountType.equals("employee")) {
//            employee.register(name,password);
//        }
//        // TODO check the return type of register to make sure account was created properly
//    }
//
//    // let the user log in to an already created account:
//    public void logIn(String accountType) {
//        // take the inputs:
//        System.out.print("Please enter your account id: ");
//        int id = scannerInt.nextInt();
//        System.out.print("Please enter a password: ");
//        String password = scanner.nextLine();
//        // execute the login command:
//        if(accountType.equals("customer")) {
//            customer.login(id,password);
//        }
//        else if (accountType.equals("employee")) {
//            employee.login(id,password);
//        }
//        // TODO check return value of login and make sure login was successful
//    }
//
//    public void apply() {
//        System.out.print("What type of account would you like to apply for? (checking, saving, etc.): ");
//        String accountName = scanner.nextLine();
//        customer.apply(accountName);
//    }
//
//    public void withdraw() {
//        System.out.println("How much money would you like to withdraw?");
//        int amount = scannerInt.nextInt();
//        System.out.println("Which account would you like to withdraw from ?");
//        // TODO, print out available accounts
//    }
//}
