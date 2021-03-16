package com.example.admin.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adapter.OrderDetailAdapter;
import com.example.admin.model.OrderModel;
import com.example.admin.model.ProductModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPreferences pref;
    OrderDetailAdapter adapter;
    String total = "";
    OrderModel Model;
    String mobile = "";
    TextView textView;
    Button cancelorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        recyclerView = findViewById(R.id.Order_detail_rv);
        cancelorder = findViewById(R.id.order_detail_button1);
        textView = findViewById(R.id.Order_detail_total);
        Model = (OrderModel) getIntent().getSerializableExtra("Model");


        textView.setText(Model.getTotal());
        adapter = new OrderDetailAdapter(this, Model.getModelList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}