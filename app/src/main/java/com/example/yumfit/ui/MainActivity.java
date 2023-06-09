package com.example.yumfit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yumfit.R;
import com.example.yumfit.authentication.register.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    TextView appNameTextView;
    Animation scaleChanger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        appNameTextView = findViewById(R.id.appNameTextView);

        scaleChanger = AnimationUtils.loadAnimation(this, R.anim.scale_changer);

        appNameTextView.startAnimation(scaleChanger);
        
        appNameTextView.postOnAnimationDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}