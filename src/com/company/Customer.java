package com.company;

import java.util.Arrays;

public class Customer extends User{

    public Customer(String name, String password) {
        super.setName(name);
        super.setPassword(password);
    }

    // constructor that takes in all fields:
    public Customer(int id, String name, String password) {
        super(id,name,password);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                '}';
    }
}
