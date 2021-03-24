package com.example.deliveryboy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.deliveryboy.R;
import com.example.deliveryboy.adapter.OrderDetailAdapter;
import com.example.deliveryboy.model.OrderModel;

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