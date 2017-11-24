package com.itmo.collections.Reflection.DIContext;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        DIContext ctx = new DIContext();
        Object b = ctx.get("com.itmo.reflection.B");

        System.out.println();
    }
}
