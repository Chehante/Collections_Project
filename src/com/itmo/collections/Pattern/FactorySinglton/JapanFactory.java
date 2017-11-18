package com.itmo.collections.Pattern.FactorySinglton;

public class JapanFactory extends Factory {
    @Override
    public Car createCar() {
        return new Toyota();
    }
}
