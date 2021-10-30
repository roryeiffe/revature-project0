package com.company;

public class Account {
    private int balance;
    private boolean verified;
    private int ownerId;

    public Account() {

    }

    public Account(int balance, boolean verified, int ownerId) {
        this.balance = balance;
        this.verified = verified;
        this.ownerId = ownerId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
