package com.example.dhobijunction.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.example.dhobijunction.R;

public class SplashActivity extends AppCompatActivity {
    ImageView imageView;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.splash_image);
        AlphaAnimation animation = new AlphaAnimation(0,1);
        //animation.setRepeatCount(4);
        animation.setDuration(500);
        getSupportActionBar().hide();
        imageView.startAnimation(animation);
        preferences = getApplicationContext().getSharedPreferences("Users",0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (preferences.contains("userMobile")){
                    Intent intent = new Intent(SplashActivity.this,BottomActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
}