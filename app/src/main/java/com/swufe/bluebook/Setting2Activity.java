package com.swufe.bluebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.swufe.bluebook.LoginOrRegister.LoginActivity;
import com.swufe.bluebook.LoginOrRegister.UpdatePaswordActivity;

import cn.bmob.v3.BmobUser;

public class Setting2Activity extends AppCompatActivity {

    LinearLayout layout1,layout2,layout3,layout4;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);

        layout1=findViewById(R.id.item_9);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Setting2Activity.this,UpdatePaswordActivity.class);
                startActivity(intent);
            }
        });
        layout3=findViewById(R.id.item_11);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Setting2Activity.this, "当前版本为1.0，已为最新版本", Toast.LENGTH_SHORT).show();
            }
        });
        layout4=findViewById(R.id.item_12);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                Intent intent = new Intent();
                intent.setClass(Setting2Activity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        toolbar=findViewById(R.id.setting_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
