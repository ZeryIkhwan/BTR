package com.zeryikhwan.btr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.zeryikhwan.btr.Action.PrefManager;

public class SplashScreen extends AppCompatActivity {

    PrefManager manager;
    int _TIMER = 1700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ActionBar myActionbar = getSupportActionBar();

        myActionbar.hide();

        manager = new PrefManager(this);
        Handler handler = new Handler();

        if (manager.sessionLogin() == true) {
            //Timer
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            }, _TIMER);
        } else {
            //Time
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent2 = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent2);
                    finish();
                }
            }, _TIMER);
        }
    }
}
