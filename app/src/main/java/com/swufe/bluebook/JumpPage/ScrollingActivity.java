package com.swufe.bluebook.JumpPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.swufe.bluebook.NotePackage.AddNoteActivity;
import com.swufe.bluebook.NotePackage.NetUtils;
import com.swufe.bluebook.R;

import pl.droidsonroids.gif.GifImageView;

public class ScrollingActivity extends AppCompatActivity {
    private WebView webView;
    private String URL,book;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Intent intent = getIntent();
        if (intent.getStringExtra("url")==null){
            URL = "http://www.moe.gov.cn/jyb_xwfb/xw_zt/moe_357/s7865/s8417/";
            book = "教育资讯";
        }else {
            URL = intent.getStringExtra("url");
            book = intent.getStringExtra("book");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(book);


//        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            int count=0;
//            @Override
//            public void onClick(View view) {
//                if(count%2==0){
//                    fab.setColorFilter(getResources().getColor(R.color.yellow));
//                    Toast.makeText(ScrollingActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
//                }else{
//                    fab.setColorFilter(getResources().getColor(R.color.white));
//                    Toast.makeText(ScrollingActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
//                }
//                count++;
//            }
//        });


        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
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

        if (NetUtils.isNetworkAvailable(ScrollingActivity.this)) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webView.getSettings().setCacheMode(
                    WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

    }
    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
