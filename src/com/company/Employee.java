package com.company;

import java.sql.SQLException;

public class Employee extends User{

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
