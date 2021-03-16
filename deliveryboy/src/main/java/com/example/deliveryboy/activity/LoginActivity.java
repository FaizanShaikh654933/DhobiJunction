package com.example.deliveryboy.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deliveryboy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tuyenmonkey.mkloader.MKLoader;

public class LoginActivity extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    MKLoader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e1=findViewById(R.id.login_email);
        e2=findViewById(R.id.login_password);
        b1=findViewById(R.id.login_button);
        loader=findViewById(R.id.login_loader);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Enter Email Id", Toast.LENGTH_SHORT).show();
                }else if(e2.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                FirebaseFirestore.getInstance().collection("DELIVERYBOY" ).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot doc :task.getResult()){
                                String a=doc.getString("email");
                                String b=doc.getString("password");
                                String a1=e1.getText().toString().trim();
                                String b1=e2.getText().toString().trim();
                                if (a.equalsIgnoreCase(a1) && b.equalsIgnoreCase(b1)){
                                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(LoginActivity.this, "Login SuccessFull", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Cannot login ,Incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });
    }
}
