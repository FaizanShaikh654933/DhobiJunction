package com.example.dhobijunction.activity;

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

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.OrderDetailAdapter;
import com.example.dhobijunction.model.OrderModel;
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
    List<OrderModel> list;
    TextView textView;
    Button cancelorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        getSupportActionBar().setTitle("Order Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.Order_detail_recyclerview);
        textView = findViewById(R.id.Order_detail_total);
        cancelorder = findViewById(R.id.order_detail_button);
        Model = (OrderModel) getIntent().getSerializableExtra("Model");
        pref = getSharedPreferences("Users", 0);

        mobile = pref.getString("userMobile", "");
        textView.setText(Model.getTotal());

        adapter = new OrderDetailAdapter(this, Model.getModelList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("USERS").document(pref.getString("userMobile",""))
                        .collection("ORDERS")
                        .whereEqualTo("orderId",Model.getOrderId())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value!=null && !value.isEmpty()){
                                    value.getDocuments().get(0).getReference().delete();
                                    Toast.makeText(OrderDetailActivity.this, "Order Cancel Succesfull", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}