package com.swufe.bluebook.TabPagers;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.squareup.picasso.Picasso;
import com.swufe.bluebook.JumpPage.ScrollingActivity;
import com.swufe.bluebook.NotePackage.NetUtils;
import com.swufe.bluebook.R;

import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class QuanJingFragment extends Fragment {

    private WebView webView;
    private String URL;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_quanjing, container, false);
        return mView;
    }
    public void updateUI(String URL){
        this.URL = URL;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.webView1);
        webView.loadUrl("http://"+URL);
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

        if (NetUtils.isNetworkAvailable(getActivity())) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webView.getSettings().setCacheMode(
                    WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


}
