package com.itmo.collections.Reflection.reflect.example;

import com.itmo.collections.Reflection.reflect.injector.Dependency;

import java.util.ArrayList;
import java.util.List;

public class TargetObject {
    @Dependency
    private Singleton singleton;

    @Dependency(singleton = false)
    private Singleton nonSingleton;

    @Dependency(type = ArrayList.class)
    private List list;

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
                ", list=" + (list == null ? "null" : list.getClass()) +
                '}';
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

}
