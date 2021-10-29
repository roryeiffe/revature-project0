package com.company;

public class Customer extends User{

    @Override
    void register(String name, String password) {
        this.loggedIn = true;
        this.name = name;
        this.password = password;
    }

    @Override
    void login(int id, String password) {
        this.loggedIn = true;
    }

    void apply(String accountName) {
        System.out.println("Your request for a " + accountName + " account is in progress.");
    }
}
