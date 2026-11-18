package com.example.proiect.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.proiect.Domain.FoodDomain;
import com.example.proiect.Interface.ChangeIndexItemsListener;

import java.util.ArrayList;

public class CartManager {
    private Context context;
    private ShoppingCartDatabase database;

    public CartManager(Context context) {
        this.context = context;
        this.database = new ShoppingCartDatabase(context);
    }


    public void insertFood(FoodDomain item) {
        ArrayList<FoodDomain> listFood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if (existAlready) {
            listFood.get(n).setIndexInCart(item.getIndexInCart());
        } else {
            listFood.add(item);
        }

        database.putListObject("CartList", listFood);
        Toast.makeText(context, "Item Added to your Cart", Toast.LENGTH_SHORT).show();
    }


    public ArrayList<FoodDomain> getListCart() {
        ArrayList<Object> objectList = database.getListObject("CartList");
        ArrayList<FoodDomain> foodDomainList = new ArrayList<>();
        for (Object object : objectList) {
            if (object instanceof FoodDomain) {
                foodDomainList.add((FoodDomain) object);
            }
        }
        return foodDomainList;
    }



        public void minusIndexFood (ArrayList < FoodDomain > listfood,
        int position, ChangeIndexItemsListener changeIndexItemsListener){
            if (listfood.get(position).getIndexInCart() == 1) {
                listfood.remove(position);
            } else {
                listfood.get(position).setIndexInCart(listfood.get(position).getIndexInCart() - 1);
            }
        }

        public void plusIndexFood (ArrayList < FoodDomain > listfood,
        int position, ChangeIndexItemsListener changeIndexItemsListener){
            listfood.get(position).setIndexInCart(listfood.get(position).getIndexInCart() + 1);
            database.putListObject("CartList", listfood);
            changeIndexItemsListener.changed();
        }

        public Double GetFee () {
            ArrayList<FoodDomain> listfood2 = getListCart();
            double fee = 0;
            for (int i = 0; i < listfood2.size(); i++) {
                fee = fee + (listfood2.get(i).getFee() + listfood2.get(i).getIndexInCart());
            }
            return fee;
        }

    }

