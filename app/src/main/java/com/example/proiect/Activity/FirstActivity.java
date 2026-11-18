package com.example.proiect.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.proiect.R;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        ConstraintLayout StartBtn = findViewById(R.id.StartBtn);
        StartBtn.setOnClickListener(v -> startActivity(new Intent(FirstActivity.this, MainActivity.class)));
    }
}