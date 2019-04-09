package com.swufe.bluebook;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swufe.bluebook.TabPagers.CiyunFragment;
import com.swufe.bluebook.TabPagers.JianJieFragment;
import com.swufe.bluebook.TabPagers.LuQuFragment;
import com.swufe.bluebook.TabPagers.QuanJingFragment;
import com.swufe.bluebook.TabPagers.QuanjingActivity;
import com.swufe.bluebook.TabPagers.SchoolInfo;
import com.swufe.bluebook.TabPagers.collegeSceneries;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ContentActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ContentViewPagerAdapter contentViewPagerAdapter;
    private Toolbar toolbar;
    private ImageView imageView, imageView1;
    private List<Fragment> mFragment;
    private TextView textView1, textView2, textView3;
    private static String TAG = "ContentActivity";
    private String university;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initView();
    }

    private void initView() {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.toolbar_content);
        imageView = findViewById(R.id.school_logo);
        textView1 = findViewById(R.id.textView4);
        textView2 = findViewById(R.id.school_name);
        textView3 = findViewById(R.id.school_labels);
        imageView1 = findViewById(R.id.quanjingtu);



        mFragment=new ArrayList<Fragment>(); //new一个List<Fragment>
        final Fragment f1 = new LuQuFragment();
        final Fragment f2 = new JianJieFragment();
        final Fragment f3 = new CiyunFragment();
        final Fragment f4 = new QuanJingFragment();//添加三个fragment到集合
        mFragment.add(f1);
        mFragment.add(f2);
        mFragment.add(f3);
        mFragment.add(f4);

        setSupportActionBar(toolbar);
        contentViewPagerAdapter = new ContentViewPagerAdapter(getSupportFragmentManager(),mFragment);
        viewPager.setAdapter(contentViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Log.i(TAG, "initView: "+getIntent().getStringExtra("university"));
        university = getIntent().getStringExtra("university");
        textView1.setText(university);
        textView2.setText(university);
        BmobQuery<SchoolInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("university",university);
        query.setLimit(50);
        query.findObjects(new FindListener<SchoolInfo>() {
            @Override
            public void done(final List<SchoolInfo> list, BmobException e) {
                if(e==null){
                    Log.i(TAG, "onCreateView: "+list);
                    Picasso.with(getApplicationContext()).load(list.get(0).getLogo()).fit().into(imageView);
                    textView3.setText(list.get(0).getType());
                    ((LuQuFragment) f1).updateUI(list.get(0).getUniversity());
                    Log.i(TAG, "done: "+list.get(0).getLocation());
                    ((JianJieFragment) f2).updateUi(list.get(0).getLocation(),list.get(0).getBelong(), list.get(0).getAcademician(), list.get(0).getDoctor(), list.get(0).getMaster(), list.get(0).getIntroduction(), list.get(0).getTeacher(), list.get(0).getOccupation());
                    ((CiyunFragment) f3).updateUi(list.get(0).getUniversity());
                    ((QuanJingFragment) f4).updateUI(list.get(0).getWebsite());

                }
            }
        });
        BmobQuery<collegeSceneries> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("university",university);
        query1.setLimit(50);
        query1.findObjects(new FindListener<collegeSceneries>() {
            @Override
            public void done(final List<collegeSceneries> list, BmobException e) {
                if(e==null){
                    imageView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.putExtra("URL",list.get(0).getCollegeScenery());
                            intent.setClass(ContentActivity.this, QuanjingActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }


}
