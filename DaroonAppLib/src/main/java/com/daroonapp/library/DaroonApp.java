package com.daroonapp.library;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import com.daroonapp.library.Transactions.TransactionsActivity;
import com.daroonapp.library.Pay.PayActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class DaroonApp extends Application {

    public static JSONObject lastJson;
    public static JSONArray allJsons;
    public static String token;
    public static int versionCode;
    public static PackageInfo pInfo;
    public static Activity mActivity;


    public static void pay(final Class mclass, final String price, final String description, final String phone, final String email) {

        final Dialog paymentBottomSheetDialog;
        paymentBottomSheetDialog = new Dialog(mActivity, com.daroonapp.library.R.style.MaterialDialogSheet);
        paymentBottomSheetDialog.setContentView(com.daroonapp.library.R.layout.payment_popup); // your custom view.
        paymentBottomSheetDialog.setCancelable(true);
        paymentBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paymentBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        paymentBottomSheetDialog.show();

        Button btnPay = (Button) paymentBottomSheetDialog.findViewById(R.id.btnpay);
        TextView txtPrice = (TextView) paymentBottomSheetDialog.findViewById(R.id.txtprice);
        TextView txtUser = (TextView) paymentBottomSheetDialog.findViewById(R.id.txtuser);
        LinearLayout linPayment = (LinearLayout) paymentBottomSheetDialog.findViewById(R.id.linpayment);

        if (email == null) {
            txtUser.setText(phone);
        } else if (phone == null) {
            txtUser.setText(email);
        } else {
            txtUser.setText(email);
        }

        txtPrice.setText(price);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentBottomSheetDialog.dismiss();
                Intent intent = new Intent(mActivity,TransactionsActivity.class);
                mActivity.startActivity(intent);
            }
        });


        linPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, PayActivity.class);
                intent.putExtra("activity", mclass.toString());
                intent.putExtra("price", price);
                intent.putExtra("description", description);
                intent.putExtra("token", token);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("packageName", mActivity.getApplicationInfo().packageName.toString());
                intent.putExtra("versionCode", String.valueOf(versionCode));

                mActivity.startActivity(intent);
            }
        });

    }

    public static JSONObject getLastTransaction() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject form1 = null;
                    try {
                        form1 = new JSONObject()
                                .put("package_name", mActivity.getApplicationInfo().packageName)
                                .put("version_code", versionCode)
                                .put("token", token);
                    } catch (Exception e) {
                    }

                    try {
                        Request request = Bridge
                                .post(Urls.trackingCode + PayActivity.id)
                                .header("Accept", "application/json")
                                .header("Content-type", "application/json")
                                .body(form1)
                                .request();
                        Response response = request.response();
                        if (response.isSuccess()) {
                            lastJson = response.asJsonObject();
                        } else {
                            lastJson = null;
                        }
                    } catch (BridgeException e) {
                    }
                } catch (Exception e) {
                    Log.e("err getLastTransaction", e + "");
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lastJson;
    }

    public static JSONArray getAllTransactions(final String number, final String email) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject form1 = null;
                    if (number != null) {
                        if (email == null) {
                            try {
                                form1 = new JSONObject()
                                        .put("package_name", mActivity.getApplicationInfo().packageName)
                                        .put("version_code", versionCode)
                                        .put("mobile", number)
                                        .put("token", token);
                            } catch (Exception e) {
                            }
                        } else {
                            try {
                                form1 = new JSONObject()
                                        .put("package_name", mActivity.getApplicationInfo().packageName)
                                        .put("version_code", versionCode)
                                        .put("email", email)
                                        .put("mobile", number)
                                        .put("token", token);
                            } catch (Exception e) {
                            }
                        }
                        try {
                            Request request = Bridge
                                    .post(Urls.allTransactions)
                                    .header("Accept", "application/json")
                                    .header("Content-Type", "application/json")
                                    .body(form1)
                                    .request();

                            Response response = request.response();
                            if (response.isSuccess()) {
                                allJsons = response.asJsonArray();
                            } else {
                                allJsons = null;
                            }

                        } catch (BridgeException e) {
                        }
                    }else{
                        Log.e("error ", "phone number is null");
                    }
                } catch (Exception e) {
                    Log.e("err all transactions", e + "");
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allJsons;


    }

    public static void init(Activity activity) {
        try {
            mActivity = activity;

            ApplicationInfo ai = mActivity.getPackageManager().getApplicationInfo(mActivity.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            token = bundle.getString("daroonApp");
            if (!token.toString().equalsIgnoreCase("")) {
                try {
                    pInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
                    versionCode = pInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                }
            } else {
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("init", e + "");
        }
    }
}
