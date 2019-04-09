package com.swufe.bluebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.OnTwoLevelListener;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.swufe.bluebook.Backstage.Ciyun;
import com.swufe.bluebook.Backstage.School;
import com.swufe.bluebook.Backstage.School;
import com.swufe.bluebook.Backstage.province;
import com.swufe.bluebook.JumpPage.ScrollingActivity;
import com.swufe.bluebook.Predict.PredictActivity;
import com.swufe.bluebook.TabPagers.ciyun;
import com.swufe.bluebook.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CardViewFragment extends Fragment{

    private static String TAG = "CardViewFragment";
    private String location;
    private ViewPager viewPager;
    private ImageView imageView;
    private View view;
    private TextView tv1,tv2;
    private ShadowTransformer mCardShadowTransformer;
    private ViewPagerAdapter adapter;
    private ImageView floor;
    private TwoLevelHeader header;
    private RefreshLayout refreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.cardview_pager, container,false);
        viewPager=view.findViewById(R.id.viewPager);
        tv1= view.findViewById(R.id.tv_bigwords);
        tv2= view.findViewById(R.id.tv_smallwords);
        imageView=view.findViewById(R.id.tupian);
        String city= (String) BmobUser.getObjectByKey("city");
        Log.i(TAG, "onCreateView: city: "+city);

        BmobQuery<province> query = new BmobQuery<>();
        query.addWhereEqualTo("city",city);
        query.setLimit(50);
        query.findObjects(new FindListener<province>() {
            @Override
            public void done(List<province> list, BmobException e) {
                if(e==null){
                    location = list.get(0).getProvince();
                    BmobQuery<Ciyun> query1 = new BmobQuery<Ciyun>();
                    query1.addWhereEqualTo("provinceName", location);
                    query1.setLimit(50);
                    query1.findObjects(new FindListener<Ciyun>() {
                        @Override
                        public void done(List<Ciyun> list, BmobException e) {
                            if(e==null){
                                Log.i(TAG, "onCreateView: "+list);
                                adapter = new ViewPagerAdapter(list,getContext());
                                Log.i(TAG, "onCreateView: adapter="+adapter);

                                mCardShadowTransformer = new ShadowTransformer(viewPager, adapter);

                                viewPager.setAdapter(adapter);
                                viewPager.setPageTransformer(false, mCardShadowTransformer);
                                viewPager.setOffscreenPageLimit(3);
                                mCardShadowTransformer.enableScaling(true);

                            }
                        }
                    });
                }
            }
        });



        return view;

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floor=view.findViewById(R.id.secondfloor);
        header=view.findViewById(R.id.header);
        refreshLayout=view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000);
            }
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                floor.setTranslationY(Math.min(offset - floor.getHeight() + 0, refreshLayout.getLayout().getHeight() - floor.getHeight()));
            }
        });
        header.setOnTwoLevelListener(new OnTwoLevelListener() {
            @Override
            public boolean onTwoLevel(@NonNull RefreshLayout refreshLayout) {
                view.findViewById(R.id.secondfloor_content).animate().alpha(1).setDuration(2000);
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        header.finishTwoLevel();
                        view.findViewById(R.id.secondfloor_content).animate().alpha(0).setDuration(1000);
                        Intent intent =new Intent(getContext(), PredictActivity.class);
                        startActivity(intent);
                    }
                },2000);
                return true;//true 将会展开二楼状态 false 关闭刷新
            }
        });
        StatusBarUtil.immersive(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}

