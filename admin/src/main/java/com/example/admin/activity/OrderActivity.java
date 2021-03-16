package com.example.admin.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adapter.OrderAdapter;
import com.example.admin.model.CategoryModel;
import com.example.admin.model.DeliveryModel;
import com.example.admin.model.OrderModel;
import com.example.admin.model.SubCategoryModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    SharedPreferences pref;
    OrderAdapter adapter;
    OrderModel Model;

    String total="";
    String mobile = "";
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView=findViewById(R.id.rv);

        getSupportActionBar().setTitle("Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        Query query = FirebaseFirestore.getInstance().collectionGroup("ORDERS");
        FirestoreRecyclerOptions<OrderModel> rvOptions = new FirestoreRecyclerOptions.Builder<OrderModel>()
                .setQuery(query, OrderModel.class).build();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new OrderAdapter(this, rvOptions, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}