package com.example.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.model.DeliveryModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DeliveryBoyActivity extends AppCompatActivity {
    EditText e1, e2,e3,e4;
    Button b1;
    DeliveryModel model;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy);
        getSupportActionBar().setTitle("Delivery Boy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        e1=findViewById(R.id.d_e1);
        e2=findViewById(R.id.d_e2);
        e3=findViewById(R.id.d_e3);
        e4=findViewById(R.id.d_e4);
        rv=findViewById(R.id.d_rv);
        b1=findViewById(R.id.d_b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model = new DeliveryModel();

                model.setName(e1.getText().toString());
                model.setPhone(e2.getText().toString());
                model.setEmail(e3.getText().toString());
                model.setPassword(e4.getText().toString());
                FirebaseFirestore.getInstance().collection("DELIVERYBOY").add(model)
                        .addOnSuccessListener(documentReference -> {
                            String docId = documentReference.getId();
                            Map<String, Object> map = new HashMap<>();
                            map.put("dId", docId);
                            documentReference.update(map).addOnSuccessListener(aVoid -> {
                                Toast.makeText(DeliveryBoyActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(DeliveryBoyActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }).addOnFailureListener(e -> {
                    Toast.makeText(DeliveryBoyActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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