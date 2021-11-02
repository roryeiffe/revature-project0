package com.company.user;

public class Employee extends User {

    // Employee constructor calls the parent constructor:
    public Employee(int id, String name, String password) {
        super(id,name,password);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                '}';
    }


}
