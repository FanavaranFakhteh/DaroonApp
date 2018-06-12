package com.daroonapp.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.daroonapp.library.DaroonApp;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // get last transaction
        JSONObject lastTransaction = DaroonApp.getLastTransaction();

        // get all of user's transaction
        JSONArray allTransactions = DaroonApp.getAllTransactions("your user's phone number","your user's email");

        Log.v("last transaction : ",lastTransaction+"");
        Log.v("all transaction : ",allTransactions+"");

    }
}
