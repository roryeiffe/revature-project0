package com.company;

public class Employee extends User{

    @Override
    void register(String name, String password) {
        this.loggedIn = true;
    }

    @Override
    void login(int id, String password) {
        this.loggedIn = true;
    }


}
