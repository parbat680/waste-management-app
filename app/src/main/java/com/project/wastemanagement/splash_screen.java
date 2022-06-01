package com.project.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class splash_screen extends AppCompatActivity {
        private FirebaseAuth mAuth;

        private FirebaseUser user;
        private final int SPLASH_SCREEN_TIME=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent mainIntent = new Intent(splash_screen.this, LoginActivity.class);
                splash_screen.this.startActivity(mainIntent);
                splash_screen.this.finish();
            }
        }, SPLASH_SCREEN_TIME);


    }

}
