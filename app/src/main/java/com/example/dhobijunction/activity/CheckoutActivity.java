package com.example.dhobijunction.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.dhobijunction.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    Spinner s1;
    String[] time = new String[]{"9:00AM-10:00AM","10:00AM-11:00AM","11:00AM-12:00PM","12:00PM-1:00PM","1:00PM-2:00PM","2:00PM-3:00PM","3:00PM-4:00PM","4:00PM-5:00PM","5:00PM-6:00PM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        s1=findViewById(R.id.checkout_spiner);
        List<String> timelist = new ArrayList<>(Arrays.asList(time));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,timelist);
        s1.setAdapter(spinnerArrayAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}