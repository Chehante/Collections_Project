package com.itmo.collections.Reflection.reflect.example;

import com.itmo.collections.Reflection.reflect.injector.Injector;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Injector injector = Injector.instance();

        TargetObject to1 = injector.getObject(TargetObject.class, false);
        TargetObject to2 = injector.getObject(TargetObject.class, false);

        System.out.println("Before injection: " + to1);
        System.out.println("Before injection: " + to2);

        injector.inject(to1);
        injector.inject(to2);

        System.out.println("After injection: " + to1);
        System.out.println("After injection: " + to2);

        System.out.println("Singleton: " + (to1.getSingleton() == to2.getSingleton())); // true
        System.out.println("NonSingleton: " + (to1.getNonSingleton() == to2.getNonSingleton())); // false
    }
}
