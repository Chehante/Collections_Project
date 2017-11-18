package com.itmo.collections.Pattern.FactorySinglton;

public class RussianFactory extends Factory{

    private static RussianFactory rusFact = new RussianFactory();

    private RussianFactory(){}

    public static RussianFactory getFactory(){return rusFact;}

    @Override
    public Car createCar() {
        return new VAZ();
    }
}
