package com.example.dhobijunction.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dhobijunction.R;
import com.example.dhobijunction.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding screen;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(screen.getRoot());
        getSupportActionBar().hide();
        screen.frgpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,forgotpasswordActivity.class);
                startActivity(intent);
            }
        });

        //t2 = findViewById(R.id.register);
        screen.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
        screen.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(screen.mobile.getText().toString().trim().length()!=10){
                    screen.mobile.setError("Enter Mobile Number");
                }else if(screen.password.getText().toString().trim().length()<=6){
                    screen.password.setError("Enter Password");
                }
                else {
                    if (screen.mobile.getText().toString().equals("7405719399") && screen.password.getText().toString().equals("Faizan123"))
                    {
                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(
                                android.R.color.transparent
                        );
                        Thread timer = new Thread() {
                            @Override
                            public void run() {

                                try {
                                    sleep(3500);
                                    Intent intent1 = new Intent(getApplicationContext(),BottomActivity.class);
                                    startActivity(intent1);
                                    progressDialog.dismiss();
                                    super.run();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        timer.start();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "UNSUCCESSFULL", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}