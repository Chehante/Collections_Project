package com.itmo.collections.Pattern.FactorySinglton;

public class BMW implements Car{
    @Override
    public int drive(int distance, int fuel) {
        return fuel * 10;
    }
}
