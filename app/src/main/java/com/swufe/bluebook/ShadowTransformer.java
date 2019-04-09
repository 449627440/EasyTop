package com.swufe.bluebook;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

public class ShadowTransformer implements ViewPager.OnPageChangeListener,ViewPager.PageTransformer {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private float mLastOffset;
    private boolean mScalingEnabled;
    private String TAG = "ShadowTransformer";

    public ShadowTransformer(ViewPager viewPager1, ViewPagerAdapter adapter) {
        viewPager = viewPager1;
        viewPager.addOnPageChangeListener(this);
        viewPagerAdapter = adapter;
        Log.i(TAG, "ShadowTransformer: viewPagerAdapter="+viewPagerAdapter);
    }

    public void enableScaling(boolean enable) {
        if (mScalingEnabled && !enable) {
            // shrink main card
            CardView currentCard = viewPagerAdapter.getCardViewAt(viewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1);
                currentCard.animate().scaleX(1);
            }
        }else if(!mScalingEnabled && enable){
            // grow main card
            CardView currentCard = viewPagerAdapter.getCardViewAt(viewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1.1f);
                currentCard.animate().scaleX(1.1f);
            }
        }

        mScalingEnabled = enable;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realCurrentPosition;
        int nextPosition;
        float baseElevation = viewPagerAdapter.getBaseElevation();
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        // Avoid crash on overscroll
        if (nextPosition > viewPagerAdapter.getCount() - 1
                || realCurrentPosition > viewPagerAdapter.getCount() - 1) {
            return;
        }

        CardView currentCard = viewPagerAdapter.getCardViewAt(realCurrentPosition);

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((float) (1 + 0.1 * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + 0.1 * (1 - realOffset)));
                Log.i(TAG, "onPageScrolled: currentCard"+(float) (1 + 0.1 * (1 - realOffset)));
            }
            currentCard.setCardElevation((baseElevation + baseElevation
                    * (8 - 1) * (1 - realOffset))/2);
        }

        CardView nextCard = viewPagerAdapter.getCardViewAt(nextPosition);

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.setScaleX((float) (1 + 0.1 * (realOffset)));
                nextCard.setScaleY((float) (1 + 0.1 * (realOffset)));
                Log.i(TAG, "onPageScrolled: nextCard"+(float) (1 + 0.1 * (realOffset)));
            }
            nextCard.setCardElevation((baseElevation + baseElevation
                    * (8 - 1) * (realOffset))/2);
        }

        mLastOffset = positionOffset;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void transformPage(@NonNull View page, float position) {

    }
}
