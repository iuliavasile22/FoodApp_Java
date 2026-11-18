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

        // Make sure a new item has a quantity of at least 1
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

    /** Returns all items in the cart, read from the database. */
    public ArrayList<FoodDomain> getListCart() {
        // FIX: the database now returns FoodDomain objects directly, no casting needed
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
        // FIX: the change is now saved and the screen is notified (both were missing)
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

    /** Sum of fee x quantity for every item in the cart. */
    public Double GetFee() {
        double fee = 0;
        for (FoodDomain food : getListCart()) {
            // FIX: was fee + quantity (addition); it should be fee * quantity
            fee += food.getFee() * food.getIndexInCart();
        }
        return fee;
    }

    /** Total value of the products in the cart (price x quantity), calculated by SQL. */
    public double getTotalPrice() {
        return database.getTotalPrice();
    }

    /** Empties the cart, e.g. after an order is placed. */
    public void clearCart() {
        database.clearCart();
    }
}