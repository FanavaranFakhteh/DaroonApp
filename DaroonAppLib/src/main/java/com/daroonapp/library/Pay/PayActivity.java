package com.daroonapp.library.Pay;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.bridge.Response;
import com.daroonapp.library.BaseActivity;
import com.daroonapp.library.R;

public class PayActivity extends BaseActivity implements PayView {
    public static ProgressBar mprogressBar;
    public static String id;
    private boolean doubleBackToExitPressedOnce = false;
    private Response response;
    private WebView mWebview;
    private TextView txtUrl;
    public static Activity activity;
    public static LinearLayout linUrl;
    PayPresenter presenter;
    Pay pay = new Pay();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            getSupportActionBar().hide();
        }catch (Exception e){}

        setContentView(R.layout.activity_pay);

        presenter = new PayPresenterImpl(this);
        viewBinding();
        setCustomChanges();
        presenter.getExtras(activity, pay);
        presenter.getResponse(pay);
    }

    public void viewBinding() {
        mWebview = (WebView) findViewById(R.id.webview);
        mprogressBar = (ProgressBar) findViewById(R.id.progressbar);
        txtUrl = (TextView) findViewById(R.id.txturl);
        linUrl = (LinearLayout) findViewById(R.id.linurl);
        activity = PayActivity.this;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            presenter.failTransaction(pay);
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

    @Override
    public void setUi(final Pay pay) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                txtUrl.setText(pay.getUrl());
                mWebview.setWebViewClient(new WebViewClient());
                mWebview.getSettings().setJavaScriptEnabled(true);

                mWebview.loadUrl(pay.getUrl());
                mWebview.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        txtUrl.setText(pay.getUrl());
                        if (url.contains("https://my.daroonapp.com/user/paid/")) {
                            presenter.endTransaction(pay);
                        }
                    }
                    public void onPageFinished(WebView view, String url) {
                        mprogressBar.setVisibility(View.GONE);
                        mWebview.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    @Override
    public void setCustomChanges() {
        setStatusBarColor();
        setProgressBarColor();
    }

    @Override
    public void setStatusBarColor() {
        Window window = PayActivity.activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#5c8ecb"));
        } else {
        }
    }

    @Override
    public void setActionBarColor(Integer integer) {
        PayActivity.linUrl.setBackgroundColor(PayActivity.activity.getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void setProgressBarColor() {
        PayActivity.mprogressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7e64de"), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void showProgressBar() {
        mprogressBar.setVisibility(View.VISIBLE);
        mWebview.setVisibility(View.GONE);
    }


}


