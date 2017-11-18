package com.itmo.collections.Pattern.FactorySinglton;

public class Toyota implements Car{
    @Override
    public int drive(int distance, int fuel) {
        return fuel * 11;
    }
}
