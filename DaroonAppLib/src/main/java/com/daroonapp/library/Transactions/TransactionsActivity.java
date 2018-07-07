package com.daroonapp.library.Transactions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.daroonapp.library.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class TransactionsActivity extends AppCompatActivity implements TransactionsView {
    JSONArray jsonArray;
    Boolean loading;
    ArrayList<Transactions> mainList;
    TransactionAdapter adapter;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    LinearLayoutManager layout;
    TransactionsPresenter transactionsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.transaction_activity);


        transactionsPresenter = new TransactionsPresenterImpl(this);
        viewBinding();
        adapter = new TransactionAdapter(getApplicationContext(), R.layout.transaction_row, mainList);
        transactionsPresenter.getInformation(TransactionsActivity.this,mainList);
    }

    @Override
    public void hideLoadingProgressBar() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setRecyclerView() {
        try {
            adapter.notifyDataSetChanged();
            layout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) { }

    }

    public void viewBinding(){
        recyclerView = (RecyclerView) findViewById(R.id.transaction_list);
        progressBar =(ProgressBar) findViewById(R.id.progressbar);
        mainList = new ArrayList<Transactions>();
    }

}
