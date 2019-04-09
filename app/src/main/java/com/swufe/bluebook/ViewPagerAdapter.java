package com.swufe.bluebook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swufe.bluebook.Backstage.Ciyun;
import com.swufe.bluebook.Backstage.School;
import com.swufe.bluebook.Backstage.School;
import com.swufe.bluebook.JumpPage.ScrollingActivity;
import com.swufe.bluebook.Moment.MomentActivity;
import com.swufe.bluebook.Moment.PublishActivity;
import com.swufe.bluebook.TabPagers.ciyun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter{
    private List<Ciyun> lists;
    private List<CardView> mViews = new ArrayList<>();
    private float mBaseElevation;
    private Context context;
    private TextView tv1,tv2;
    private ImageView imageView;
    private CardView cardView;
    private static String TAG = "ViewPagerAdapter";
    private int MAX_ELEVATION_FACTOR = 8;

    public ViewPagerAdapter(List<Ciyun> lists, Context context) {
        this.lists = lists;
        this.context = context;
        for(int i=0;i<this.lists.size();i++){
            mViews.add(null);
        }

    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.view_pager_item, container, false);
        container.addView(view);
        Log.i(TAG, "instantiateItem: position="+position);
        tv1=view.findViewById(R.id.tv_bigwords);
        tv2=view.findViewById(R.id.tv_smallwords);
        imageView=view.findViewById(R.id.tupian);
        tv1.setText(lists.get(position).getDaxue());
        tv2.setText(lists.get(position).getXiaoxun());
        Picasso.with(context).load(lists.get(position).getShoutu()).fit().into(imageView);
        cardView=view.findViewById(R.id.cardView);
        Log.i(TAG, "instantiateItem: cardView="+cardView);
        Log.i(TAG, "instantiateItem: mBaseElevation="+mBaseElevation);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent config = new Intent();
                config.setClass(context,ContentActivity.class);
                config.putExtra("university",lists.get(position).getDaxue());
//                config.setClass(context,PublishActivity.class);
                context.startActivity(config);
            }
        });
        return view;
    }



    public CardView getCardViewAt(int position) {
        Log.i(TAG, "getCardViewAt: mViews="+mViews);
        Log.i(TAG, "getCardViewAt: position="+position);
        return mViews.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }
}
