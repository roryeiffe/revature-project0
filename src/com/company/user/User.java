package com.company.user;

public class User{
    // All users will have these fields:
    private int id;
    private String name;
    private String password;

    // Default constructor:
    public User() {
    }

    // Constructor that takes in all fields:
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // Getter and setter methods for attributes:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
