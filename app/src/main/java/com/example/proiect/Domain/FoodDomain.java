package com.example.proiect.Domain;

import java.io.Serializable;

public class FoodDomain implements Serializable {
    private int IndexInCart;
    private String title;
    private String picFood;
    private String description;
    private Double price;
    private Double fee;
    private int star;

    private Double calories;
    private int time;
    private String type;


    public FoodDomain(int IndexInCart, String title, String picFood, String description, String type, Double price, Double fee, int star, Double calories, int time) {
        this.IndexInCart = IndexInCart;
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
        return IndexInCart;
    }

    public void setIndexInCart(int indexInCart) {
        IndexInCart = indexInCart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }
    public String getPicFood() {
        return picFood;
    }

    public void setPicFood(String picFood) {
        this.picFood = picFood;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}



