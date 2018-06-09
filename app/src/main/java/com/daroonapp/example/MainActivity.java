package com.daroonapp.example;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.daroonapp.library.DaroonApp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btngo;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;
        viewBinding();

        // verify account
        DaroonApp.init(MainActivity.this);

        btngo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btngo: // payment
                DaroonApp.pay(ResultActivity.class, "price", "Description", "your user's phone number", "your user's email");
                break;
        }
    }

    public void viewBinding() {
        btngo = (Button) findViewById(R.id.btngo);
    }
}

