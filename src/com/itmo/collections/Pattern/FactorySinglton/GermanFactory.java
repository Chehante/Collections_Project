package com.itmo.collections.Pattern.FactorySinglton;

public class GermanFactory extends Factory {

    @Override
    public Car createCar() {
        return new BMW();
    }
}
