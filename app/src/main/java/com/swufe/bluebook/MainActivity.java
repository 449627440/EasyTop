package com.swufe.bluebook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.squareup.picasso.Picasso;
import com.swufe.bluebook.Advise.FeedbackActivity;
import com.swufe.bluebook.Backstage.School;
import com.swufe.bluebook.Backstage.province;
import com.swufe.bluebook.CityChooseSlide.AddressActivity;
import com.swufe.bluebook.Collect.CollectActivity;
import com.swufe.bluebook.Handbook.HandbookActivity;
import com.swufe.bluebook.JumpPage.ScrollingActivity;
import com.swufe.bluebook.LoginOrRegister.LoginActivity;
import com.swufe.bluebook.LoginOrRegister.UpdatePaswordActivity;
import com.swufe.bluebook.Moment.MomentActivity;
import com.swufe.bluebook.NotePackage.Main2Activity;
import com.swufe.bluebook.PersonalCenter.Photo.PersonalCenterActivity;
import com.swufe.bluebook.TabPagers.SchoolInfo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private NavigationView navigationview;
    private DrawerLayout drawerlayout;
    private ImageButton imageButton;

    /*创建一个Drawerlayout和Toolbar联动的开关*/
    private ActionBarDrawerToggle drawerToggle;
    private String location;
    private static String CityName;

    private static String TAG= "MainActivity";

    TextView textView1;
    ImageView floor;
    TwoLevelHeader header;
    RefreshLayout refreshLayout;
    View headerLayout;
    ImageView headCircle;
    TextView nicname1;

    ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();

    private Fragment mFragments[];
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1=findViewById(R.id.title);
        toolbar = findViewById(R.id.simple_toolbar);
        toolbar.setTitle("");

        navigationview = findViewById(R.id.navigation_view);
        headerLayout=navigationview.getHeaderView(0);
        headCircle=headerLayout.findViewById(R.id.headview);
        nicname1=headerLayout.findViewById(R.id.nicnameview);


        drawerlayout=findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设置左上角图标是否可点击
            actionBar.setHomeButtonEnabled(true);
            //左上角加上一个返回图标
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerlayout, toolbar, 0, 0);
        drawerToggle.syncState();
        drawerlayout.setDrawerListener(drawerToggle);
        navigationview.setNavigationItemSelectedListener(this);


        mFragments = new Fragment[2];
        manager = getSupportFragmentManager();
        Log.i(TAG, "onCreate: "+list);



        mFragments[0]=manager.findFragmentById(R.id.fragment_cardview);
        mFragments[1]=manager.findFragmentById(R.id.fragment_listview);


        transaction = manager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]);
        transaction.show(mFragments[0]).commit();

        BmobQuery<province> query = new BmobQuery<>();
        query.addWhereEqualTo("city",BmobUser.getObjectByKey("city"));
        query.setLimit(50);
        query.findObjects(new FindListener<province>() {
            @Override
            public void done(List<province> list, BmobException e) {
                if(e==null){
                    location = list.get(0).getProvince();
                    textView1.setText(location);
                }
            }
        });

        Log.i(TAG, "onResume: "+BmobUser.getObjectByKey("city"));
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,AddressActivity.class);
                startActivity(intent);
            }
        });


        imageButton=findViewById(R.id.switch1);
        imageButton.setOnClickListener(new View.OnClickListener() {

            int count=0;
            @Override
            public void onClick(View v) {

                RotateAnimation animation = new RotateAnimation(0.0f, 360.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration( 400 );
                imageButton.startAnimation( animation );

                count++;
                Log.i(TAG, "onClick: "+count);
                if(count%2==1){
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setCustomAnimations(R.anim.push_left_in,R.anim.push_left_out);
                    ft.hide(fm.findFragmentById(R.id.fragment_cardview));
                    ft.show(fm.findFragmentById(R.id.fragment_listview));
                    ft.addToBackStack(null);
                    ft.commit();
                }else if (count%2==0){
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
                    ft.hide(fm.findFragmentById(R.id.fragment_listview));
                    ft.show(fm.findFragmentById(R.id.fragment_cardview));
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        Log.i(TAG, "onOptionsItemSelected: true");
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        int id = item.getItemId();
        switch (id) {
            case R.id.item_1:
                intent.setClass(this,AddressActivity.class);
                startActivity(intent);
                break;
            case R.id.item_2:
                intent.setClass(this,PersonalCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.item_3:
                intent.setClass(this,Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.item_4:
                intent.setClass(this, ScrollingActivity.class);
                startActivity(intent);
                break;
            case R.id.item_5:
                intent.setClass(this,Setting2Activity.class);
                startActivity(intent);
                break;

        }
        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //如果是服务里调用，必须加入new task标识
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Picasso.with(this).load((String) BmobUser.getObjectByKey("headPortrait")).into(headCircle);
        nicname1.setText((String)BmobUser.getObjectByKey("nickname"));
    }


}

