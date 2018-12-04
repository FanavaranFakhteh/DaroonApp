package com.daroonapp.example;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.daroonapp.library.Transactions.TransactionsActivity;
import com.daroonapp.library.DaroonApp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btngo,btnTransactions;
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
        btnTransactions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btngo: // payment
                DaroonApp.pay(NextActivity.class, "your_price", "(description for developer)", "user's phone number", "user's email");
                break;
            case R.id.btntransactions:
                Intent intent = new Intent(getApplicationContext(),TransactionsActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void viewBinding() {
        btngo = (Button) findViewById(R.id.btngo);
        btnTransactions = (Button) findViewById(R.id.btntransactions);
    }
}

