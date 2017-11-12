package com.itmo.collections.Shop;

public class Transaction {
    private double total;
    private User usr;

    public Transaction(double total, User usr){
        this.total = total;
        this.usr = usr;
    }

    @Override
    public String toString() {
        return usr.toString() + ": " + total;
    }
}
