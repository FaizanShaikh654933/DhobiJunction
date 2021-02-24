package com.example.dhobijunction.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.OrderDetailAdapter;
import com.example.dhobijunction.model.OrderModel;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPreferences pref;
    OrderDetailAdapter adapter;
    String total = "";
    OrderModel Model;
    String mobile = "";
    List<OrderModel> list;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.Order_detail_recyclerview);
        textView = findViewById(R.id.Order_detail_total);

        Model = (OrderModel) getIntent().getSerializableExtra("Model");
        pref = getSharedPreferences("Users", 0);

        mobile = pref.getString("userMobile", "");
        textView.setText(Model.getTotal());

        adapter = new OrderDetailAdapter(this, Model.getModelList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}