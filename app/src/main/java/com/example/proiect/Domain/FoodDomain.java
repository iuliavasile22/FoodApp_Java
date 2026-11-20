package com.example.proiect.Domain;

import java.io.Serializable;

public class FoodDomain implements Serializable {
    private int indexInCart;
    private String title;
    private final String picFood;
    private final String description;
    private final Double price;
    private final  Double fee;
    private final  int star;

    private final Double calories;
    private final int time;
    private final String type;


    public FoodDomain(int indexInCart, String title, String picFood, String description, String type,
                      Double price, Double fee, int star, Double calories, int time) {
       this.indexInCart = indexInCart;
        this.title = title;
        this.picFood = picFood;
        this.description = description;
        this.type = type;
        this.price = price;
        this.fee = fee;
        this.star = star;
        this.calories = calories;
        this.time = time;


    }

    public int getIndexInCart() {

        return indexInCart;
    }

    public void setIndexInCart(int indexInCart) {

        this.indexInCart = indexInCart;
    }

    public String getTitle() {

        return title;
    }
    public String getPicFood(){
        return picFood;
    }

    public  String getDescription(){
        return description;
    }
    public String getType(){
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public Double getFee() {
        return fee;
    }
    public int getStar(){
        return star;
    }

    public double getCalories(){
        return calories;
    }

    public int getTime(){
        return time;
    }
}