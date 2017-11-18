package com.itmo.collections.Pattern.FactorySinglton;

abstract class Factory {
    private static Factory rusFactory;
    private static Factory japFactory;
    private static Factory gerFactory;

    public abstract Car createCar();

    public static Factory getFactory(String country){
        Factory factory = null;
        switch (country) {
            case "RU":
                if (rusFactory == null)
                    rusFactory = new RussianFactory();
                factory = rusFactory;
                break;
            case "DE":
                if (gerFactory == null)
                    gerFactory = new GermanFactory();
                factory = gerFactory;
                break;
            case "JP":
                if (japFactory == null)
                    japFactory = new JapanFactory();
                factory = japFactory;
                break;
        }
        return factory;
    }

    public static void main(String[] args) {
        Factory rusFact = Factory.getFactory("RU");
        Car vaz = rusFact.createCar();
        int realMillage = vaz.drive(100, 10);

        Factory rusFact2 = Factory.getFactory("RU");
        Car vaz2 = rusFact.createCar();
        int realMillage2 = vaz.drive(100, 10);

        System.out.println(realMillage + "" + realMillage2);
    }
}
