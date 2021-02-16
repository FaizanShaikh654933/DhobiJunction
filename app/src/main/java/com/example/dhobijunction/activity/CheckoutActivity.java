package com.example.dhobijunction.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.CartAdapter;
import com.example.dhobijunction.adapter.CheckoutAdapter;
import com.example.dhobijunction.model.CartModel;
import com.example.dhobijunction.model.CheckoutModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    Button b1;
    SharedPreferences pref;
    String mobile = "";
    RecyclerView recyclerView;
    CheckoutAdapter adapter;
    Spinner s1;
    String[] time = new String[]{"9:00AM-10:00AM","10:00AM-11:00AM","11:00AM-12:00PM","12:00PM-1:00PM","1:00PM-2:00PM","2:00PM-3:00PM","3:00PM-4:00PM","4:00PM-5:00PM","5:00PM-6:00PM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        b1 = findViewById(R.id.btn_payment);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckoutActivity.this,PaymentActivity.class));
            }
        });

        pref = getSharedPreferences("Users",0);
        mobile = pref.getString("userMobile","");

        s1=findViewById(R.id.checkout_spiner);
        List<String> timelist = new ArrayList<>(Arrays.asList(time));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,timelist);
        s1.setAdapter(spinnerArrayAdapter);

        Query query = FirebaseFirestore.getInstance().collection("USERS")
                .document(mobile).collection("USERCART");
        FirestoreRecyclerOptions<CheckoutModel> rvOptions = new FirestoreRecyclerOptions.Builder<CheckoutModel>()
                .setQuery(query, CheckoutModel.class).build();

        recyclerView = findViewById(R.id.cart_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CheckoutAdapter(this, rvOptions, this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}