package com.swufe.bluebook.Moment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.swufe.bluebook.Backstage.User;
import com.swufe.bluebook.Moment.VIew.GradationScrollView;
import com.swufe.bluebook.Moment.VIew.NoScrollListview;
import com.swufe.bluebook.Moment.VIew.SpaceAdapter;
import com.swufe.bluebook.Moment.VIew.TaskCompletedEvent;
import com.swufe.bluebook.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MomentActivity extends AppCompatActivity implements GradationScrollView.ScrollViewListener{

    private ImageView backGroundImg,imageView;
    private GradationScrollView scrollView;
    private Toolbar spaceTopChange;
    private int height;
    private SpaceAdapter adapter;
    private List<TaskCompletedEvent> orders;
    private NoScrollListview spaceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);
        intiView();
        initListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        intiData();
    }

    /**
     * 初始化控件
     */
    private void intiView() {
        spaceList= (NoScrollListview) findViewById(R.id.spaceList);

        backGroundImg= (ImageView) findViewById(R.id.backGroundImg);
        backGroundImg.setFocusable(true);
        backGroundImg.setFocusableInTouchMode(true);
        backGroundImg.requestFocus();
        imageView=findViewById(R.id.btn_addcomment);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MomentActivity.this,PublishActivity.class);
                startActivity(intent);
            }
        });

        scrollView = (GradationScrollView) findViewById(R.id.scrollview);
        spaceTopChange= (Toolbar) findViewById(R.id.spaceTopChange);
        adapter=new SpaceAdapter(this,orders);
        spaceList.setAdapter(adapter);
    }
    /**
     * 查询数据
     */
    private void intiData() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<TaskCompletedEvent> query =new BmobQuery<>();
        query.addWhereEqualTo("user",user);
        query.order("-createdAt");
        query.include("user");
        query.include("task");
        query.findObjects(new FindListener<TaskCompletedEvent>() {
            @Override
            public void done(List<TaskCompletedEvent> list, BmobException e) {
                if(e==null){
                    orders=list;
                    adapter.addOrder(orders);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {
        spaceTopChange.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ViewTreeObserver vto = backGroundImg.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                spaceTopChange.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = backGroundImg.getHeight();

                scrollView.setScrollViewListener(MomentActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 滑动监听
     * 根据滑动的距离动态改变标题栏颜色
     */
}

