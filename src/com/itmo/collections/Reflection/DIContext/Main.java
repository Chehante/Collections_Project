package com.itmo.collections.Reflection.DIContext;

import com.itmo.collections.Reflection.Utils;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        DIContext ctx = DIContext.getInstance();
        B b = ctx.get("com.itmo.collections.Reflection.DIContext.B");

        System.out.println(Utils.toString(b));

        String randomString = b.getI().getValue();

        System.out.println(randomString);

        I i = (I)ctx.get("com.itmo.collections.Reflection.DIContext.Impl");

        System.out.println(Utils.toString(i));

        assert i == b.getI(); // singleton
    }
}
