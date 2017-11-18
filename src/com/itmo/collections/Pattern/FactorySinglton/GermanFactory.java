package com.itmo.collections.Pattern.FactorySinglton;

public class GermanFactory extends Factory {

    private static GermanFactory gerFac = new GermanFactory();

    public static GermanFactory getFactory() {
        return gerFac;
    }

    private GermanFactory() {
    }

    @Override
    public Car createCar() {
        return new BMW();
    }
}
