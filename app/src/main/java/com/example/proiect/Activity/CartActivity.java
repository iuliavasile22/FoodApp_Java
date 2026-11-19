package com.example.proiect.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import  com.example.proiect.Helper.CartManager;
import com.example.proiect.R;

import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    private static final double Delivery_Tax = 8.0; //delivery fee
    private static final double Tax_Rate = 0.05; // 5% tax on the products

    private CartManager cartManager;

    private RecyclerView recyclerViewCart;
    private TextView productsTxt, deliveryTxt, taxTxt, totalTxt, checkoutBtn;
    private ImageView backBtn;

    @Override
   protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartManager = new CartManager(this);

        initView();
        initList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        calculateCart();
    }

    private void initView() {
        recyclerViewCart = findViewById(R.id.view);
        productsTxt = findViewById(R.id.feePerItem);
        deliveryTxt = findViewById(R.id.deliveryText);
        taxTxt = findViewById(R.id.taxText);
        totalTxt = findViewById(R.id.total);
        checkoutBtn = findViewById(R.id.textView6);
        backBtn = findViewById(R.id.back_button);

        //return to the previous screen
        backBtn.setOnClickListener(v -> finish());

        checkoutBtn.setOnClickListener(v -> {
            if (cartManager.getListCart().isEmpty()) {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            cartManager.clearCart();
            Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show();
            calculateCart();
        });
    }

    private void initList() {
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void calculateCart() {
        double products = cartManager.getTotalPrice();
        double delivery = products > 0 ? Delivery_Tax : 0;
        double tax = products * Tax_Rate;
        double total = products + delivery + tax;

        productsTxt.setText(priceFormat(products));
        deliveryTxt.setText(priceFormat(delivery));
        taxTxt.setText(priceFormat(tax));
        totalTxt.setText(priceFormat(total));
    }

    private String priceFormat(double value){
        return String.format(Locale.US,"$%.2f", value);
    }
}

