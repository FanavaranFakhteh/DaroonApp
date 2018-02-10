package com.daroonapp.library;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class DaroonApp {

    public static JSONObject lastJson;
    public static JSONArray allJsons;
    public static String token;
    public static int versionCode;
    public static PackageInfo pInfo;
    public static Context mcontext;

    public static void pay(Class mclass, String price, String description, String phone, String email){
        Intent intent = new Intent(mcontext, PayActivity.class);
        intent.putExtra("activity",mclass.toString());
        intent.putExtra("price",price);
        intent.putExtra("description",description);
        intent.putExtra("token",token);
        intent.putExtra("phone",phone);
        intent.putExtra("email",email);
        intent.putExtra("packageName",mcontext.getApplicationInfo().packageName.toString());
        intent.putExtra("versionCode",String.valueOf(versionCode));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mcontext.startActivity(intent);
    }

    public static JSONObject getLastTransaction() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject form1 = null;
                    try {
                        form1 = new JSONObject()
                                .put("package_name", mcontext.getApplicationInfo().packageName)
                                .put("version_code", versionCode)
                                .put("token", token);
                    } catch (Exception e) {}

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
                    } catch (BridgeException e) {}
                } catch (Exception e) {
                    Log.e("getLastTransaction", e+"");
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
                    if (email == null) {
                        try {form1 = new JSONObject()
                                    .put("package_name", mcontext.getApplicationInfo().packageName)
                                    .put("version_code", versionCode)
                                    .put("mobile", number)
                                    .put("token", token);
                        } catch (Exception e) {}
                    }else if(number == null){
                        try {form1 = new JSONObject()
                                .put("package_name", mcontext.getApplicationInfo().packageName)
                                .put("version_code", versionCode)
                                .put("email", email)
                                .put("token", token);
                        } catch (Exception e) {}
                    }else{
                        try {form1 = new JSONObject()
                                .put("package_name", mcontext.getApplicationInfo().packageName)
                                .put("version_code", versionCode)
                                .put("email", email)
                                .put("mobile", number)
                                .put("token", token);
                        } catch (Exception e) {}
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

                    } catch (BridgeException e) {}
                } catch (Exception e) {
                    Log.e("getAllTransactions", e+"");
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

    public static void init(Context context){
        try {
            mcontext = context;
            ApplicationInfo ai = mcontext.getPackageManager().getApplicationInfo(mcontext.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            token = bundle.getString("daroonApp");
            if (!token.toString().equalsIgnoreCase("")){
                try {
                    pInfo = mcontext.getPackageManager().getPackageInfo(mcontext.getPackageName(), 0);
                    versionCode = pInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {}
            }else{}
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("init", e+"");
        }
    }
}
