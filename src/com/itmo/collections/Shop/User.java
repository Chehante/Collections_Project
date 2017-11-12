package com.itmo.collections.Shop;

import com.itmo.collections.Shop.Product;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String password;
    private double account;
    public HashMap<Product, Integer> userCart = new HashMap<>();

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

    public double getAccount(){
        return account;
    }

    public void setAccount(double acc){
        account = acc;
        System.out.println("Account of " + name + " is " + account);
    }

    public void addToCart(HashMap <Product, Integer> hm){
        if (hm.size() > 0)
            System.out.println("Was added to cart:");
        for (Map.Entry<Product, Integer> entry : hm.entrySet()){
            userCart.merge(entry.getKey(), entry.getValue(), (integer, integer2) -> integer + integer2);
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public void showCartBalance(){
        System.out.println("Product balance in user's cart is:");
        for (Map.Entry<Product, Integer> entry: userCart.entrySet())
            System.out.println(entry.getKey().toString() + ", quantity - " + entry.getValue());
    }

    @Override
    public String toString() {
        return getName();
    }
}
