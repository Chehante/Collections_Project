package com.itmo.collections.Pattern.FactorySinglton;

public class RussianFactory extends Factory{
    @Override
    public Car createCar() {
        return new VAZ();
    }
}
