package com.example.proiect.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proiect.Domain.FoodDomain;
import com.example.proiect.Helper.CartManager;
import com.example.proiect.R;

public class ShowDetailActivity extends AppCompatActivity {

    private TextView addToCartBtn;
    private TextView titleTxt, priceTxt, descriptionTxt, numberOrderTxt, totalPriceTxt, starTxt, caloryTxt, timeTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private FoodDomain obj;
    private int numberOrder = 1;
    private CartManager cartManager;
    ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        cartManager = new CartManager(this);

        initView();
        getBundle();
    }

    private void getBundle() {
// Retrieve the serialized object from the previous activity
        obj = (FoodDomain) getIntent().getSerializableExtra("obj");

int drawableResourceId = this.getResources().getIdentifier(obj.getPicFood(), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picFood);

        titleTxt.setText(obj.getTitle());
        priceTxt.setText("$" + obj.getPrice());
        descriptionTxt.setText(obj.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));
        caloryTxt.setText(obj.getCalories() +" cal");
        starTxt.setText(obj.getStar() + "");
        timeTxt.setText(obj.getTime() +" mins");

        plusBtn.setOnClickListener(v -> {
            numberOrder = numberOrder + 1;
            numberOrderTxt.setText(String.valueOf(numberOrder));
            totalPriceTxt.setText(String.valueOf(numberOrder * obj.getPrice()));
        });

        minusBtn.setOnClickListener(v -> {
            if(numberOrder > 1){
                numberOrder = numberOrder - 1;
            }
            numberOrderTxt.setText(String.valueOf(numberOrder));
            totalPriceTxt.setText(String.valueOf(numberOrder * obj.getPrice()));
        });
        addToCartBtn.setOnClickListener(v -> {
            obj.setIndexInCart(numberOrder);   // save the quantity chosen on screen
            cartManager.insertFood(obj);       // write to SQLite (CartManager shows the toast itself)
        });
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.AddToCartBtn);
        titleTxt = findViewById(R.id.title_txt);
        priceTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberItemT);
        plusBtn = findViewById(R.id.plusCartBtn);
        minusBtn = findViewById(R.id.minusCartBtn);
        picFood = findViewById(R.id.picFood);
        totalPriceTxt = findViewById(R.id.totalPriceTxt);
        starTxt = findViewById(R.id.starTxt);
        caloryTxt = findViewById(R.id.txtCalories);
        timeTxt = findViewById(R.id.timeTxt);
        back_button = findViewById(R.id.back_btn);

        back_button.setOnClickListener(v -> {
            Intent n = new Intent(ShowDetailActivity.this, MainActivity.class);
            startActivity(n);
            finish();
        });
    }
}