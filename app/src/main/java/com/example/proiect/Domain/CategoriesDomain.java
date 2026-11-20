package com.example.proiect.Domain;

public class CategoriesDomain{
    private final String title;
    private final String pic;

    public CategoriesDomain(String title, String pic){
        this.title = title;
        this.pic = pic;
    }

    public String getTitle(){
        return title;
    }

    public String getPic(){
        return pic;
    }
}
