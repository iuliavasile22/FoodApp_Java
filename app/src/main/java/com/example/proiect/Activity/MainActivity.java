package com.example.proiect.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiect.Adapter.CategoryAdapter;
import com.example.proiect.Adapter.PopularFoodAdapter;
import com.example.proiect.Domain.CategoriesDomain;
import com.example.proiect.Domain.FoodDomain;
import com.example.proiect.Helper.ShoppingCartDatabase;
import com.example.proiect.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularFoodList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recyclerViewPopularFood();

        findViewById(R.id.cartButton).setOnClickListener(v ->
                startActivity(new Intent(this, CartActivity.class)));
    }

    private void recyclerViewPopularFood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularFoodList= findViewById(R.id.view2);
        recyclerViewPopularFoodList.setLayoutManager(linearLayoutManager);


        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain(1, "hamburger", "burger", "a delicious hamburger with tasty flavour!", "hamburger",15.5, 10.2, 5 , 40.2, 30));
        foodList.add(new FoodDomain(1, "vegan pizza", "vegan_p", "a delicious, healthy vegan pizza!", "vegan", 13.2,10.2, 4,  20.5, 20));
        foodList.add(new FoodDomain(1, "pepperoni", "pizza1", "a delicious pepperoni pizza! ", "pepperoni", 10.2, 10.2, 4, 25.5, 35));

        adapter2 = new PopularFoodAdapter(foodList);
        recyclerViewPopularFoodList.setAdapter(adapter2);

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList = findViewById(R.id.view1);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoriesDomain> categoriesList = new ArrayList<>();
        categoriesList.add(new CategoriesDomain("Burger", "hamburger"));
        categoriesList.add(new CategoriesDomain("Pizza", "pizza"));
        categoriesList.add(new CategoriesDomain("Hotdog", "hotd"));
        categoriesList.add(new CategoriesDomain("Donut", "donut"));
        categoriesList.add(new CategoriesDomain("Drink", "soda"));

        adapter = new  CategoryAdapter(categoriesList);
        recyclerViewCategoryList.setAdapter(adapter);

    }
    private void testCartDatabase() {
        ShoppingCartDatabase db = new ShoppingCartDatabase(this);

        // 1. Start from an empty cart
        db.clearCart();

        // 2. Insert two items
        long id1 = db.insertFoodItem(new FoodDomain(0, "hamburger", "burger",
                "a delicious hamburger", "hamburger", 15.5, 10.2, 5, 40.2, 30));
        long id2 = db.insertFoodItem(new FoodDomain(0, "vegan pizza", "vegan_p",
                "a healthy vegan pizza", "vegan", 13.2, 10.2, 4, 20.5, 20));
        Log.d("CART_TEST", "Inserted ids: " + id1 + ", " + id2);   // -1 means the insert failed

        // 3. Read everything back
        for (FoodDomain f : db.getAllFoodItems()) {
            Log.d("CART_TEST", "Item: " + f.getTitle() + " | price " + f.getPrice()
                    + " | photo " + f.getPicFood());
        }

        // 4. Check count and total
        Log.d("CART_TEST", "Count: " + db.getItemCount());     // expected 2
        Log.d("CART_TEST", "Total: " + db.getTotalPrice());    // expected 28.7

        // 5. Delete one and check again
        db.deleteItem((int) id1);
        Log.d("CART_TEST", "Count after delete: " + db.getItemCount());  // expected 1
    }

}