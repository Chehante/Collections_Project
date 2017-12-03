package com.itmo.multithreading.Sinchronized;

public class Pizzeria {

    Pizza pizza;

    public static void main(String[] args) throws InterruptedException {
        Pizza pizza = new Pizza.Builder(15).cheese(10).makePizza();
        Pizzeria pizzeria = new Pizzeria();
        Client client = pizzeria.new Client("Sam");
        Thread threadWaiter = pizzeria.new Waiter();
        threadWaiter.start();
        Thread threadCooker = pizzeria.new Cooker();
        threadCooker.start();
        synchronized (threadWaiter){
            System.out.println("Making order with waiter");
            Thread.sleep(500);
            threadWaiter.notify();
        }
        synchronized (threadCooker){
            threadCooker.wait();

        }



    }

    public class Client{

        private String name;

        public Client(String name) {
            this.name = name;
        }

        public void makeOrder(){
            System.out.println("Clinet");
        }

    }

    public class Waiter extends Thread{
//        @Override
//        public void run() {
//            synchronized (this){
//                try {
//                    this.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Going to cooker");
//                synchronized ()
//            }
//        }
    }

    public class Cooker extends Thread{
        @Override
        public void run() {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Making pizza");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Pizza pizza = new Pizza.Builder(15).cheese(10).makePizza();
            System.out.println("Pizza done");
        }
    }

    public static class Pizza {

        private final int diametr;
        private final int tomato;
        private final int cheese;
        private final int mushrooms;


        public static class Builder {
            private final int diametr;
            private int tomato = 0;
            private int cheese = 0;
            private int mushrooms = 0;

            public Builder( int diametr){
                this.diametr = diametr;
            }

            public Builder tomato (int val){
                tomato = val;
                return this;
            }

            public Builder cheese ( int val){
                cheese = val;
                return this;
            }

            public Builder mushrooms ( int val) {
                mushrooms = val;
                return this;
            }

            public Pizza makePizza () {
                return new Pizza(this);
            }

        }

        private Pizza(Builder builder) {
            diametr = builder.diametr;
            tomato = builder.tomato;
            cheese = builder.cheese;
            mushrooms = builder.mushrooms;
        }
    }
}
