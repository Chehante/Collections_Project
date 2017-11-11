package com.itmo.collections.Shop;

public class User {
    private String name;
    private String password;
    private int account;

    public User (String name, String password, int account){
        this.name = name;
        this.password = password;
        this.account = account;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }

    public Integer getAccount(){
        return account;
    }

    public void setAccount(Integer acc){
        account = acc;
    }
}
