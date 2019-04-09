package com.swufe.bluebook.TabPagers;

import android.graphics.Bitmap;
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
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swufe.bluebook.NotePackage.NetUtils;
import com.swufe.bluebook.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class JianJieFragment extends Fragment {
    private String location;
    private String lishu;
    private String yuanshi;
    private String boshi;
    private String shuoshi;
    private String introduction;
    private String school;
    private String major;
    private String teacher;
    private String occupation;

    private TextView textView1, textView2, textView3, textView4;
    private TextView textView5, textView6;
    private TextView textView9, textView10;

    public void updateUi(String location,String lishu, String yuanshi,String boshi, String shuoshi, String introduction, String teacher, String  occupation){
        textView1.setText(location);
        Log.i("Jianjie", "onViewCreated: "+location);
        textView2.setText(lishu);
        textView3.setText(yuanshi);
        textView4.setText(boshi);
        textView5.setText(shuoshi);
        textView6.setText(introduction);
        textView9.setText(teacher);
        textView10.setText(occupation);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_jianjie, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Jianjie", "onViewCreated: "+location);
        textView1 = view.findViewById(R.id.location1);
        textView2 = view.findViewById(R.id.lishu1);
        textView3 = view.findViewById(R.id.yuanshi1);
        textView4 = view.findViewById(R.id.boshi1);
        textView5 = view.findViewById(R.id.shuoshi1);
        textView6 = view.findViewById(R.id.introduction1);
        textView6.setLineSpacing(2,1.5f);
        textView9 = view.findViewById(R.id.teacher1);
        textView9.setLineSpacing(2,1.5f);
        textView10 = view.findViewById(R.id.occupation1);
        textView10.setLineSpacing(2,1.5f);

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
