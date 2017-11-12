package com.itmo.collections.Shop;

public class Product {
    private String title;
    private String description;
    private double price;

    @Override
    public String toString() {
        return getTitle() + " (price: " + getPrice() + ")";
    }

    public Product(String title, String description, Integer price){
        this.title = title;
        this.description = description;
        this.price = price;

    }

    public String getTitle(){
        return title;
    }

    public Double getPrice(){
        return price;
    }
}
