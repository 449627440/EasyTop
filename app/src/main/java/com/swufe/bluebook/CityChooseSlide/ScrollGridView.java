package com.swufe.bluebook.CityChooseSlide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class ScrollGridView extends GridView {
	public ScrollGridView(Context context) {
		super(context);
	}

	public ScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			return true;// 禁止Gridview进行滑动
		}
		return super.dispatchTouchEvent(ev);
	}

}
