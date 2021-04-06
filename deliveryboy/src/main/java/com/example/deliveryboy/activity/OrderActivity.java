package com.example.deliveryboy.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.deliveryboy.R;
import com.example.deliveryboy.adapter.OrderAdapter;
import com.example.deliveryboy.model.DeliveryModel;
import com.example.deliveryboy.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    SharedPreferences pref;
    OrderAdapter adapter;
    OrderModel Model;
    SharedPreferences preferences;
    DeliveryModel deliveryModel;
    String total="";
    String mobile = "";
    RecyclerView recyclerView;
    List<DeliveryModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView=findViewById(R.id.o_rv);
        preferences=getSharedPreferences("DELIVERYBOY",MODE_PRIVATE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseFirestore.getInstance().collection("DELIVERYBOY").whereEqualTo("dId",preferences.getString("dId","")).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value!=null && !value.isEmpty()){

                    Query query=value.getDocuments().get(0).getReference().collection("Order");
                    FirestoreRecyclerOptions<OrderModel> rvOptions = new FirestoreRecyclerOptions.Builder<OrderModel>()
                            .setQuery(query, OrderModel.class).build();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new OrderAdapter(OrderActivity.this, rvOptions, OrderActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.stopListening();
                    adapter.startListening();
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}