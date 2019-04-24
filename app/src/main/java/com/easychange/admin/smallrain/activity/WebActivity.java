package com.easychange.admin.smallrain.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.qlzx.mylibrary.base.BaseActivity;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.widget.LoadingLayout;


import bean.AssementReviewBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.KeyEvent.KEYCODE_BACK;


/**
 * Created by guo on 2017/5/11.
 * web
 */

public class WebActivity extends AppCompatActivity {
    @BindView(R.id.web_view)
    WebView mWebView;
    private String url;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.hide();  //隐藏掉标题栏
        }
        ButterKnife.bind(this);
        initView();
        //initmWebView();
    }

    public void initView() {

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        //设置webview的屏幕适配
        mWebView.getSettings().setDomStorageEnabled(true);//打开DOM存储API
        WebSettings settings = mWebView.getSettings();

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(true);
        settings.getJavaScriptCanOpenWindowsAutomatically();
        mWebView.addJavascriptInterface(this, "android");

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
            }
        });
        mWebView.loadUrl(url);
    }

    public static void startActivity(Context context, String title, String url){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }
    public static String getPrecisionStandardTime(){
        long millis = System.currentTimeMillis();
        return String.valueOf(millis);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void goBack() {
        getIsRemindDialog(new PreferencesHelper(WebActivity.this).getToken());
        Log.e("webactivity","js调用的方法");
    }
    public void getIsRemindDialog(String token) {
        HttpHelp.getInstance().create(RemoteApi.class).getAssessmentReview(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<AssementReviewBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<AssementReviewBean> assementReviewBeanBaseBean) {
                        super.onNext(assementReviewBeanBaseBean);
                        AssementReviewBean assementReviewBean = null;
                        if (assementReviewBeanBaseBean.code == 200) {
                            Log.e("webactivity","js调用的方法1");
                            finish();
                        } else if (assementReviewBeanBaseBean.code == 205 || assementReviewBeanBaseBean.code == 209) {
                            Log.e("webactivity","js调用的方法2");
                            GoToLoginActivityUtils.tokenFailureLoginOut(WebActivity.this);

                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        Log.e("webactivity","js调用的方法3");
                        GoToLoginActivityUtils.tokenFailureLoginOut(WebActivity.this);
                    }
                });
           }

    @JavascriptInterface
    public void goBacktwo() {
        finish();
        Log.e("webactivity","js调用的方法");
    }

    @JavascriptInterface
    public void goBackthree() {
        finish();
        Log.e("webactivity","js调用的方法");
    }

    @JavascriptInterface
    public void goBackfour() {
        finish();
        Log.e("webactivity","js调用的方法");
    }

    @JavascriptInterface
    public void goBackfive() {
        finish();
        Log.e("webactivity","js调用的方法");
    }

    @JavascriptInterface
    public void goBacksix() {
        finish();
        Log.e("webactivity","js调用的方法");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
