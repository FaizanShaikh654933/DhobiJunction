package com.example.admin.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.adapter.SubCategoryAdapter;
import com.example.admin.model.CategoryModel;
import com.example.admin.model.SubCategoryModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoryActivity extends AppCompatActivity {
    Spinner spinner;
    EditText e1;
    Button b1;
    RecyclerView recyclerView;
    SubCategoryModel model;
    List<CategoryModel> list = new ArrayList<>();
    List<String> Categorylist = new ArrayList<>();
    List<SubCategoryModel> modelList;
    SubCategoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        spinner = findViewById(R.id.sub_spinner);
        e1 = findViewById(R.id.subcategory_edittext);
        b1 = findViewById(R.id.subcategory_button);
        recyclerView = findViewById(R.id.subcategory_recyclerview);

        FirebaseFirestore.getInstance().collection("CATEGORY").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value !=null && !value.isEmpty()) {
                    list =value.toObjects(CategoryModel.class);
                    for (CategoryModel category:list){
                        Categorylist.add(category.getTitle());
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(SubCategoryActivity.this,R.layout.support_simple_spinner_dropdown_item,Categorylist);
                    spinner.setAdapter(arrayAdapter);

                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model =new SubCategoryModel();

                for (int i=0;i<list.size();i++){
                    if (list.get(i).getTitle()==spinner.getSelectedItem().toString()){
                        model.setcId(list.get(i).getcId());
                        model.setTitle(e1.getText().toString());


                        FirebaseFirestore.getInstance().collection("SUB_CATEGORY" ).add(model).addOnSuccessListener(documentReference -> {
                            String docId = documentReference.getId();
                            Map<String, Object> map = new HashMap<>();
                            map.put("sId", docId);
                            documentReference.update(map).addOnSuccessListener(aVoid -> {
                                Toast.makeText(SubCategoryActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(SubCategoryActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }).addOnFailureListener(e -> {
                            Toast.makeText(SubCategoryActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        });
                    }
                }


            }
        });
        FirebaseFirestore.getInstance().collection("SUB_CATEGORY").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && !value.isEmpty()) {
                    modelList=value.toObjects(SubCategoryModel.class);
                    adapter=new SubCategoryAdapter(SubCategoryActivity.this,modelList);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SubCategoryActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(linearLayoutManager);

                }
            }
        });

    }
}