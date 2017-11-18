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
                    rusFactory = RussianFactory.getFactory();
                factory = rusFactory;
                break;
            case "DE":
                if (gerFactory == null)
                    gerFactory = GermanFactory.getFactory();
                factory = gerFactory;
                break;
            case "JP":
                if (japFactory == null)
                    japFactory = JapanFactory.getFactory();
                factory = japFactory;
                break;
        }
        return factory;
    }

    public static void main(String[] args) {
        Factory rusFact = Factory.getFactory("RU");
        Car vaz = rusFact.createCar();
        int rusMillage = vaz.drive(100, 10);

        Factory gerFact = Factory.getFactory("DE");
        Car bmw = gerFact.createCar();
        int gerMillage = bmw.drive(100, 10);

        Factory japFact = Factory.getFactory("JP");
        Car toy = japFact.createCar();
        int japMillage = toy.drive(100, 10);


        System.out.println("Cars will go miles: ");
        System.out.println("VAZ");
        System.out.println(Integer.toString(rusMillage));
        System.out.println("BMW");
        System.out.println(Integer.toString(gerMillage));
        System.out.println("TOYOTA");
        System.out.println(Integer.toString(japMillage));
    }
}
