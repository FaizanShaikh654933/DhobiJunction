package com.example.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.admin.R;

public class DeliveryBoyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy);
        getSupportActionBar().setTitle("Delivery Boy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}