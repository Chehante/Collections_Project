package com.itmo.collections.Reflection;

import com.itmo.collections.Shop.User;

public class ExampleClass {

    public int a;

    @Exclude
    public int b;

    public String c;

    public User usr;

    public ExampleClass(int a, int b, String c, User usr){
        this.a = a;
        this.b = b;
        this.c = c;
        this.usr = usr;
    }

    public static void main(String[] args) throws IllegalAccessException {
        ExampleClass ex = new ExampleClass(2,3,"4", new User("Kir", "pas", 100));
        System.out.println(Utils.toString(ex));

    }

}
