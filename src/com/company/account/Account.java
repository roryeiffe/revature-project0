package com.company.account;

public class Account {
    // id of this account:
    private int id;
    // name of this account (checking, savings, etc.)
    private String name;
    // the customer who owns this account:
    private int ownerId;
    // how much money is stored in this account:
    private int balance;
    // status can be unverified or unverified
    private String status;


    // default constructor:
    public Account() {

    }

    // constructor that takes in all fields:
    public Account(int id, String name, int ownerId, int balance, String status) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.status = status;
        this.ownerId = ownerId;
    }

    // constructor that just takes name and ownerId and automatically sets the other values
    // to default:
    public Account(String name, int ownerId) {
        this.name = name;
        this.ownerId = ownerId;
        this.balance = 0;
        this.status = "unverified";
    }

    // getter and setter methods:
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                '}';
    }
}
