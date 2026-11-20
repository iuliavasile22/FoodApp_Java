package com.example.proiect.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.proiect.Domain.FoodDomain;
import com.example.proiect.Interface.ChangeIndexItemsListener;

import java.util.ArrayList;

public class CartManager {
    private static final String CART_KEY = "CartList";

    private final Context context;
    private final ShoppingCartDatabase database;

    public CartManager(Context context) {
        this.context = context;
        this.database = new ShoppingCartDatabase(context);
    }

    /** Adds an item to the cart. If it's already there, its quantity is updated instead. */
    public void insertFood(FoodDomain item) {
        ArrayList<FoodDomain> listFood = getListCart();

        if (item.getIndexInCart() < 1) {
            item.setIndexInCart(1);
        }

        boolean existAlready = false;
        for (FoodDomain food : listFood) {
            if (food.getTitle().equals(item.getTitle())) {
                food.setIndexInCart(item.getIndexInCart());
                existAlready = true;
                break;
            }
        }
        if (!existAlready) {
            listFood.add(item);
        }

        database.putListObject(CART_KEY, listFood);
        Toast.makeText(context, "Item added to your cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<FoodDomain> getListCart() {
        return database.getListObject(CART_KEY);
    }

    /** Decreases the quantity of an item; removes it when the quantity reaches 0. */
    public void minusIndexFood(ArrayList<FoodDomain> listFood, int position,
                               ChangeIndexItemsListener changeIndexItemsListener) {
        if (listFood.get(position).getIndexInCart() == 1) {
            listFood.remove(position);
        } else {
            listFood.get(position).setIndexInCart(listFood.get(position).getIndexInCart() - 1);
        }

        database.putListObject(CART_KEY, listFood);
        changeIndexItemsListener.changed();
    }

    /** Increases the quantity of an item. */
    public void plusIndexFood(ArrayList<FoodDomain> listFood, int position,
                              ChangeIndexItemsListener changeIndexItemsListener) {
        listFood.get(position).setIndexInCart(listFood.get(position).getIndexInCart() + 1);
        database.putListObject(CART_KEY, listFood);
        changeIndexItemsListener.changed();
    }

    public void removeFood(String title){
        ArrayList<FoodDomain> listFood = getListCart();
        listFood.removeIf(food -> food.getTitle().equals(title));
        database.putListObject(CART_KEY, listFood);
    }

    public Double GetFee() {
        double fee = 0;
        for (FoodDomain food : getListCart()) {
            fee += food.getFee() * food.getIndexInCart();
        }
        return fee;
    }

    /** Total value of the products in the cart (price x quantity), calculated by SQL. */
    public double getTotalPrice() {
        return database.getTotalPrice();
    }

    public void clearCart() {
        database.clearCart();
    }
}