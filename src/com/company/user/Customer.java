package com.company.user;

public class Customer extends User {

    // Constructor that just takes name and password:
    public Customer(String name, String password) {
        super.setName(name);
        super.setPassword(password);
    }

    // constructor that takes in all fields:
    public Customer(int id, String name, String password) {
        super(id,name,password);
    }

    // Small change to string method, print out "Customer"
    // instead of "User"
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                '}';
    }
}
