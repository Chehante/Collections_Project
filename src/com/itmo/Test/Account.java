package com.itmo.Test;

public class Account {

    private int id;
    private int balance;
    private int userID;

    public Account(int id, int balance, int userID) {
        this.id = id;
        this.balance = balance;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public int getUserID() {
        return userID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
