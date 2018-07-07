package com.daroonapp.library.Pay;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import com.daroonapp.library.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class PayPresenterImpl implements PayPresenter{
    Response response;
    PayView payView;
    Activity mActivity;

    public PayPresenterImpl(PayView payView) {
        this.payView = payView;
    }

    @Override
    public void failTransaction(final Pay pay) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject form1 = null;
                    try {
                        form1 = new JSONObject()
                                .put("package_name", pay.getPackageName())
                                .put("version_code", pay.versionCode)
                                .put("token", pay.getToken());
                    } catch (Exception e) {}
                    try {
                        Request request = Bridge
                                .post(Urls.failTransaction + pay.getId())
                                .header("Accept", "application/json")
                                .header("Content-type", "application/json")
                                .body(form1)
                                .request();
                        Response response = request.response();
                        if (response.isSuccess()) {
                            endTransaction(pay);
                        }
                    } catch (BridgeException e) {}
                } catch (Exception e) {}
            }
        });
        thread.start();
    }

    @Override
    public void getResponse(final Pay pay) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (pay.getPhone() != null) {
                    try {
                        JSONObject form1 = null;
                        try {
                            if (pay.getEmail() == null) {
                                form1 = new JSONObject()
                                        .put("package_name", pay.getPackageName())
                                        .put("version_code", pay.getVersionCode())
                                        .put("extra_data", pay.getDescription())
                                        .put("amount", pay.getPrice())
                                        .put("mobile", pay.getPhone())
                                        .put("type", "1")
                                        .put("token", pay.getToken());
                            } else {
                                form1 = new JSONObject()
                                        .put("package_name", pay.getPackageName())
                                        .put("version_code", pay.getVersionCode())
                                        .put("extra_data", pay.getDescription())
                                        .put("amount", pay.getPrice())
                                        .put("mobile", pay.getPhone())
                                        .put("email", pay.getEmail())
                                        .put("type", "1")
                                        .put("token", pay.getToken());
                            }

                        } catch (Exception e) { }
                        try {
                            Request request = Bridge
                                    .post(Urls.makePayment)
                                    .header("Accept", "application/json")
                                    .header("Content-type", "application/json")
                                    .body(form1)
                                    .request();
                            response = request.response();
                            if (response.isSuccess()) {
                                JSONObject object = null;
                                object = new JSONObject(String.valueOf(response.asJsonObject()));
                                try {
                                   pay.setId(String.valueOf(object.getInt("payment_id")));
                                   pay.setUrl(Urls.zarinPayment + pay.getId());
                                   payView.setUi(pay);
                                } catch (JSONException e1) { }
                            }
                        } catch (BridgeException e) { }
                    } catch (Exception e) { }
                }else{
                    Log.e("error ","phone number is null");
                }
            }
        });
        thread.start();
    }

    @Override
    public void getExtras(Activity activity,Pay pay) {
        mActivity = activity;
        Intent intent = activity.getIntent();
        pay.setActivity(intent.getStringExtra("activity"));
        pay.setPackageName(intent.getStringExtra("packageName"));
        pay.setPrice(intent.getStringExtra("price"));
        pay.setDescription(intent.getStringExtra("description"));
        pay.setToken(intent.getStringExtra("token"));
        pay.setPhone(intent.getStringExtra("phone"));
        pay.setEmail(intent.getStringExtra("email"));
        pay.setVersionCode(intent.getStringExtra("versionCode"));
    }

    @Override
    public void endTransaction(Pay pay) {
        String destinationActivity = pay.getActivity();
        destinationActivity = destinationActivity.replace("class ", "");
        Class<?> className = null;
        try {
            className = Class.forName(destinationActivity);
        } catch (ClassNotFoundException e) {
        }

        Intent intent = new Intent(mActivity, className);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
    }
}
