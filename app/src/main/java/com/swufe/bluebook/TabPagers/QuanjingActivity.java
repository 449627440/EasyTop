package com.swufe.bluebook.TabPagers;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.tencent.smtt.sdk.WebSettings;

import com.swufe.bluebook.NotePackage.NetUtils;
import com.swufe.bluebook.R;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class QuanjingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private WebView webView;
    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        URL = getIntent().getStringExtra("URL");
        setContentView(R.layout.activity_quanjing);
        webView = findViewById(R.id.webView);
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(URL);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view,url,favicon);
                webView.setVisibility(View.GONE);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(false);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(webView.getContext().getCacheDir().getAbsolutePath());

        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片

        if (NetUtils.isNetworkAvailable(getApplication())) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webView.getSettings().setCacheMode(
                    WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
