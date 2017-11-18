package com.itmo.collections.Pattern.FactorySinglton;

public class JapanFactory extends Factory {

    private static JapanFactory japFac = new JapanFactory();

    private JapanFactory() {
    }

    public static JapanFactory getFactory() {
        return japFac;
    }

    @Override
    public Car createCar() {
        return new Toyota();
    }
}
