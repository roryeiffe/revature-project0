package com.company;

import java.util.Arrays;

public class Customer{
    // instance variables:
    private int id;
    private String name;
    private String password;
    // status indicates wether they can use their account:
    private String status;
    // array of ids that correspond to accounts:
    int [] accountIds;

    public Customer() {

    }

    // constructor that takes in all fields:
    public Customer(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // setter and getter methods:
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", accountIds=" + Arrays.toString(accountIds) +
                '}';
    }
}
