package com.daroonapp.library.Transactions;

import android.app.Activity;
import android.os.AsyncTask;

import com.daroonapp.library.DaroonApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransactionsPresenterImpl implements TransactionsPresenter{
    TransactionsView transactionsView;
    JSONArray jsonArray;
    Activity mActivity;
    JSONObject jObject;
    String price,status,hours,date,transactionId,appName;
    ArrayList<Transactions> mainList;

    public TransactionsPresenterImpl(TransactionsView transactionsView) {
        this.transactionsView = transactionsView;
    }

    @Override
    public void getInformation(Activity activity, ArrayList<Transactions> arrayList) {
        mActivity = activity;
        mainList = arrayList;
        jsonArray = DaroonApp.getAllTransactions("09372503980", null);
        new JSONAsyncTask().execute();
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                if (jsonArray.length() == 0) {
                    mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            transactionsView.hideLoadingProgressBar();
                        }
                    });
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    try { jObject = jsonArray.getJSONObject(i); } catch (JSONException e) { }
                    try { price = jObject.getString("amount"); } catch (JSONException e) { }
                    try { status = jObject.getString("status"); } catch (JSONException e) { }
                    try { transactionId = jObject.getString("transaction_id"); } catch (JSONException e) { }
                    try { hours = jObject.getString("pay_time"); } catch (JSONException e) { }
                    try { date = jObject.getString("updated_at"); } catch (JSONException e) { }

                    Transactions transactions = new Transactions();

                    transactions.setName(appName);
                    transactions.setPrice(price);
                    transactions.setStatus(status);
                    transactions.setId(transactionId);
                    transactions.setDate(date);

                    mainList.add(transactions);
                }
            } catch (Exception e) { transactionsView.hideLoadingProgressBar(); }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            transactionsView.setRecyclerView();
            transactionsView.hideLoadingProgressBar();
        }
    }
}
