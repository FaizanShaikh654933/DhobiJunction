package com.example.admin.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adapter.CategoryAdapter;
import com.example.admin.model.CategoryModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {
    EditText e1;
    Button b1;
    CategoryModel model;
    RecyclerView recyclerView;
    List<CategoryModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        e1 = findViewById(R.id.category_edittext);
        b1 = findViewById(R.id.category_button);
        recyclerView = findViewById(R.id.category_recyclerview);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model = new CategoryModel();

                model.setTitle(e1.getText().toString());
                FirebaseFirestore.getInstance().collection("CATEGORY").add(model)
                        .addOnSuccessListener(documentReference -> {
                            String docId = documentReference.getId();
                            Map<String, Object> map = new HashMap<>();
                            map.put("cId", docId);
                            documentReference.update(map).addOnSuccessListener(aVoid -> {
                                Toast.makeText(CategoryActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(CategoryActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }).addOnFailureListener(e -> {
                    Toast.makeText(CategoryActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        FirebaseFirestore.getInstance().collection("CATEGORY").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && !value.isEmpty()) {
                    list = value.toObjects(CategoryModel.class);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CategoryActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this, list);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}