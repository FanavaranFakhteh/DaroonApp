package com.daroonapp.library;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class PayActivity extends BaseActivity {
    public static ProgressBar mprogressBar;
    public static String id;
    private String email, phone, description, price, token, url, destinationActivity,packageName,versionCode;
    private boolean doubleBackToExitPressedOnce = false;
    private Response response;
    private WebView mWebview;
    private EditText edturl;
    public static Activity activity;
    public static LinearLayout linUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pay);

        viewBinding();
        setCustomChanges();
        getExtras(activity);
        getResponse();
    }

    public void viewBinding() {
        mWebview = (WebView) findViewById(R.id.webview);
        mprogressBar = (ProgressBar) findViewById(R.id.progressbar);
        edturl = (EditText) findViewById(R.id.edturl);
        linUrl = (LinearLayout) findViewById(R.id.linurl);
        activity = PayActivity.this;
    }

    public void getResponse() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject form1 = null;
                    try {
                        if(email == null) {
                            form1 = new JSONObject()
                                    .put("package_name", packageName)
                                    .put("version_code", String.valueOf(versionCode))
                                    .put("extra_data", description)
                                    .put("amount", price)
                                    .put("mobile", phone)
                                    .put("type", "1")
                                    .put("token", token);
                        }else if (phone == null){
                            form1 = new JSONObject()
                                    .put("package_name", packageName)
                                    .put("version_code", String.valueOf(versionCode))
                                    .put("extra_data", description)
                                    .put("amount", price)
                                    .put("email", email)
                                    .put("type", "1")
                                    .put("token", token);
                        }else{
                            form1 = new JSONObject()
                                    .put("package_name", packageName)
                                    .put("version_code", String.valueOf(versionCode))
                                    .put("extra_data", description)
                                    .put("amount", price)
                                    .put("mobile", phone)
                                    .put("email", email)
                                    .put("type", "1")
                                    .put("token", token);
                        }
                    } catch (Exception e) {}
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
                                id = String.valueOf(object.getInt("payment_id"));
                                url = Urls.zarinPayment + id;
                                setUi();
                            } catch (JSONException e1) {}
                        }
                    } catch (BridgeException e) {}
                } catch (Exception e) {}
            }
        });
        thread.start();
    }

    public void setUi() {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                edturl.setText(url);
                mWebview.setWebViewClient(new WebViewClient());
                mWebview.getSettings().setJavaScriptEnabled(true);

                mWebview.loadUrl(url);
                mWebview.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        edturl.setText(url);
                        if (url.contains("https://daroonapp.com/user/paid/")) {
                            endTransaction();
                        }
                    }

                    public void onPageFinished(WebView view, String url) {
                        mprogressBar.setVisibility(View.GONE);
                        mWebview.setVisibility(View.VISIBLE);
                    }
                });
                edturl.setEnabled(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            failTransaction();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "در صورت اطمینان مجددا کلیک نمایید", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void failTransaction() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject form1 = null;
                    try {
                        form1 = new JSONObject()
                                .put("package_name", packageName)
                                .put("version_code", versionCode)
                                .put("token", token);
                    } catch (Exception e) {}
                    try {
                        Request request = Bridge
                                .post(Urls.failTransaction + id)
                                .header("Accept", "application/json")
                                .header("Content-type", "application/json")
                                .body(form1)
                                .request();
                        Response response = request.response();
                        if (response.isSuccess()) {
                            endTransaction();
                        }
                    } catch (BridgeException e) {}
                } catch (Exception e) {}
            }
        });
        thread.start();

    }

    public void getExtras(Activity activity) {
        Intent intent = activity.getIntent();
        price = intent.getStringExtra("price");
        description = intent.getStringExtra("description");
        token = intent.getStringExtra("token");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        packageName = intent.getStringExtra("packageName");
        versionCode = intent.getStringExtra("versionCode");
        destinationActivity = intent.getStringExtra("activity");
    }

    public void endTransaction() {
            destinationActivity = destinationActivity.replace("class ","");
            Class<?> className = null;
            try {className = Class.forName(destinationActivity);} catch (ClassNotFoundException e) {}

            Intent intent = new Intent(this, className);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    }

    public void setCustomChanges(){
        statusBarColor(Global.statusBarColor);
        actionBarColor(Global.actionBarColor);
        progressBarColor(Global.progressBarColor);
    }

    public static void statusBarColor(Integer integer){
        if(Global.statusBarColor == 0){}
        else {
            Window window = PayActivity.activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(PayActivity.activity, integer));
            } else {
            }
        }
    }

    public static void actionBarColor(Integer integer){
        if(Global.actionBarColor == 0){}
        else {
            PayActivity.linUrl.setBackgroundColor(PayActivity.activity.getResources().getColor(integer));
        }
    }

    public static void progressBarColor(Integer integer){
        if(Global.progressBarColor == 0) {}
        else{
            PayActivity.mprogressBar.getIndeterminateDrawable().setColorFilter(PayActivity.activity.getResources()
                    .getColor(integer), PorterDuff.Mode.SRC_IN);
        }
    }
}
