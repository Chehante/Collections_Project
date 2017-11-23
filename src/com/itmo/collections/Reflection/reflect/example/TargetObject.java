package com.itmo.collections.Reflection.reflect.example;

import com.itmo.collections.Reflection.reflect.injector.Dependency;

import java.util.ArrayList;
import java.util.List;

public class TargetObject {

    @Dependency(singleton = false)
    private Singleton nonSingleton;

    @Dependency
    private Singleton singleton;

    private int i = 10;

    public void process() {
        System.out.println(singleton);
        System.out.println(nonSingleton);
    }

    public Singleton getSingleton() {
        return singleton;
    }

    public void setSingleton(Singleton singleton) {
        this.singleton = singleton;
    }

    public Singleton getNonSingleton() {
        return nonSingleton;
    }

    public void setNonSingleton(Singleton nonSingleton) {
        this.nonSingleton = nonSingleton;
    }

    @Override
    public String toString() {
        return "TargetObject{" +
                "singleton=" + singleton +
                ", nonSingleton=" + nonSingleton +
                '}';
    }

}
