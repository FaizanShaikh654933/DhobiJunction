package com.example.dhobijunction.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.adminAdapter;
import com.example.dhobijunction.model.adminModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class admin extends AppCompatActivity {
    EditText e1,e2;
    RecyclerView r1;
    Button b1;
    adminModel model;
    adminAdapter adapter;
    List<adminModel> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        r1 = findViewById(R.id.r1);
        b1 = findViewById(R.id.b1);
        adapter=new adminAdapter(this,list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        r1.setAdapter(adapter);
        r1.setLayoutManager(linearLayoutManager);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model = new adminModel();
                model.setcId(e2.getText().toString());
                model.setTitle(e1.getText().toString());
                FirebaseFirestore.getInstance().collection("CATEGORY").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {
                            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    Toast.makeText(admin.this, "Succesfull", Toast.LENGTH_SHORT).show();

                                }
                            });
                    }
                });
            }
        });


    }
}