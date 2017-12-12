package com.itmo.Test;

public class User {

    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public void notification(Account acc, int amount){
        System.out.println("It was " + (amount > 0 ? " added " : " substracted ") + amount + " on account " + acc.getId() + " of " + name);
        System.out.println("Account " + acc.getId() + " balance is " + acc.getBalance());
    }
}
