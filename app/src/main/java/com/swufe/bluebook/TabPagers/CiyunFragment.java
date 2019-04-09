package com.swufe.bluebook.TabPagers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.swufe.bluebook.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CiyunFragment extends Fragment {
    private ImageView imageView;
    private String university;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_ciyun, container, false);
        return mView;
    }

    public void updateUi(String University){
        university = University;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.ciyun);
        BmobQuery<ciyun> query = new BmobQuery<>();
        query.addWhereEqualTo("university",university);
        query.setLimit(50);
        query.findObjects(new FindListener<ciyun>() {
            @Override
            public void done(List<ciyun> list, BmobException e) {
                if(e==null){
                    Log.i("ciyun", "onCreateView: "+list);
                    Picasso.with(getContext()).load(list.get(0).getCiyun()).into(imageView);
                }
            }
        });

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
