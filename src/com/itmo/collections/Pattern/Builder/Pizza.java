package com.itmo.collections.Pattern.Builder;

public class Pizza {

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

    public static void main(String[] args) {

        Pizza peperoni = new Builder(15).cheese(10).makePizza();

        Pizza mush = new Pizza.Builder(10).cheese(8).mushrooms(15).makePizza();

    }

}
