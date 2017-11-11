package com.itmo.collections.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Shop {
    private int account;
    private HashMap<Product, Integer> productMap;
    private List<User> usersList;
    User currentUser;

    //contructor
    private Shop (List<User> users, HashMap<Product, Integer> productMap){
        usersList = users;
        this.productMap = productMap;
    }

    public static void main(String[] args){

        //make users list
        List<User> list = new ArrayList<User>();
        for (int i = 0; i< 3; i++){
            User u = new User("user" + i, "password" + i, 100);
            list.add(u);
        }

        //make product list
        HashMap<Product, Integer> hm = new HashMap<>();
        hm.put(new Product("Onion", "red onion small size", 3), 10);
        hm.put(new Product("Bread", "black", 1), 30);
        hm.put(new Product("Tomato", "", 5), 20);
        hm.put(new Product("Cucumber", "", 3), 20);
        hm.put(new Product("Chees", "parmezan", 8), 2);
        hm.put(new Product("Milk", "", 4), 12);
        hm.put(new Product("Yogurt", "", 2), 20);
        hm.put(new Product("Fish", "", 7), 15);


        Shop shp = new Shop(list, hm);

        User u = shp.loginUser("user1", "password1");

        


    }

    public User loginUser(String log, String pass){

        for(User u : usersList){
            if (u.getName() == log && u.getPassword() == pass);
            System.out.println("You are logon with user: " + u.getName());
            currentUser = u;
        }
        System.out.println("User or password is wrong");
        return null;

    }


}
