package com.itmo.collections.Reflection.DIContext;

public class Impl implements I {
    @Override
    public String getValue() {
        return String.valueOf(Math.random());
    }
}
