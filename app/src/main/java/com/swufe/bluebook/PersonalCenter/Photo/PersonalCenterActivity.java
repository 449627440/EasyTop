package com.swufe.bluebook.PersonalCenter.Photo;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.swufe.bluebook.Moment.PublishActivity;
import com.swufe.bluebook.PersonalCenter.Photo.CityList.MyPlace;
import com.swufe.bluebook.PersonalCenter.Photo.CityList.MyPlaceAdapter;

import com.swufe.bluebook.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class PersonalCenterActivity extends BaseActivity {


    @Bind(R.id.personalcenter_toolbar)
    Toolbar toolbar;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_personal_center);
    }

    @Override
    protected void initViews() {
        initMainFragment();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initMainFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BaseFragment mFragment = MainFragment.newInstance();
        transaction.replace(R.id.main_act_container, mFragment, mFragment.getFragmentName());
        transaction.commit();
    }

    @Override
    public void initEvents() {


    }


}
