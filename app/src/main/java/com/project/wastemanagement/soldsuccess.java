package com.project.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class soldsuccess extends AppCompatActivity {
    private Handler handler;
    private Integer SPLASH_SECONDS=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soldsuccess);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.movein);
        a.reset();
        TextView tv = (TextView) findViewById(R.id.anim);
        TextView message=(TextView) findViewById(R.id.anim2);

        tv.clearAnimation();
        tv.startAnimation(a);

        message.clearAnimation();
        message.startAnimation(a);

        handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(soldsuccess.this,Home_Activity.class));
                finish();
            }
        },SPLASH_SECONDS);

    }
}