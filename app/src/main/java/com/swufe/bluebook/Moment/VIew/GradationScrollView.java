package com.swufe.bluebook.Moment.VIew;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Andream on 2017/10/19.
 * 自定义滑动框
 */
public class GradationScrollView extends ScrollView {

    public interface ScrollViewListener {

        void onScrollChanged(GradationScrollView scrollView, int x, int y,
                             int oldx, int oldy);

    }

    private ScrollViewListener scrollViewListener = null;

    public GradationScrollView(Context context) {
        super(context);
    }

    public GradationScrollView(Context context, AttributeSet attrs,
                               int defStyle) {
        super(context, attrs, defStyle);
    }

    public GradationScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}

