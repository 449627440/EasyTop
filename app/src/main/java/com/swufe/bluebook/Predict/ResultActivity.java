package com.swufe.bluebook.Predict;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swufe.bluebook.Backstage.School;
import com.swufe.bluebook.Backstage.Score;
import com.swufe.bluebook.Backstage.User;
import com.swufe.bluebook.R;
import com.swufe.bluebook.TabPagers.SchoolInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ResultActivity extends AppCompatActivity {

    private static String TAG = "ResultActivity";
    private ConstraintLayout constraintLayout1, constraintLayout2, constraintLayout3;
    private TextView textView,textView1,textView2,textView3,textView4, textView5,textView6;
    private String location;
    private String wenlike;
    private String pici;
    private Toolbar toolbar;
    private ImageView imageView;
    private int score;
    private float max;
    private float min;
    private float mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = findViewById(R.id.tv_score);
        textView1 = findViewById(R.id.num_chong);
        textView2 = findViewById(R.id.num_wen);
        textView3 = findViewById(R.id.num_bao);
        textView4 = findViewById(R.id.tv_province);
        textView5 = findViewById(R.id.tv_type);
        textView6 = findViewById(R.id.xiugai);
        imageView = findViewById(R.id.xiugai_logo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar = findViewById(R.id.result_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        constraintLayout1 = findViewById(R.id.chongci);
        constraintLayout2 = findViewById(R.id.wentuo);
        constraintLayout3 = findViewById(R.id.baodi);

        location = (String) BmobUser.getObjectByKey("location");
        score = Integer.parseInt((String) BmobUser.getObjectByKey("score"));
        wenlike = (String) BmobUser.getObjectByKey("wenlike");
        pici = (String) BmobUser.getObjectByKey("pici");
        textView.setText(String.valueOf(score));
        textView4.setText(location);
        textView5.setText(wenlike);

        max = (float) (score*(1+0.015));
        mid = (float) (score*(1-0.01));
        min = (float) (score*(1-0.03));
        Log.i(TAG, "onCreate: "+max);
        Log.i(TAG, "onCreate: "+mid);
        Log.i(TAG, "onCreate: "+min);

        final BmobQuery<Score> query = new BmobQuery<>();
        query.addWhereEqualTo("year", "2019");
        query.addWhereEqualTo("type",wenlike);
        query.addWhereEqualTo("province",location);
        query.addWhereEqualTo("order",pici);
        query.addWhereGreaterThanOrEqualTo("aveScore", score);
        query.addWhereLessThanOrEqualTo("aveScore",max);
        query.findObjects(new FindListener<Score>() {
            @Override
            public void done(final List<Score> list, BmobException e) {
                textView1.setText(String.valueOf(list.size()));
                if(list.size()!=0){
                    final ArrayList<SchoolInfo> listObj = new ArrayList<SchoolInfo>();
                    for(int i = 0;i<list.size();i++){
                        BmobQuery<SchoolInfo> query1 = new BmobQuery<>();
                        query1.addWhereEqualTo("university",list.get(i).getUniversity());
                        query1.findObjects(new FindListener<SchoolInfo>() {
                            @Override
                            public void done(List<SchoolInfo> list, BmobException e) {
                                SchoolInfo info = new SchoolInfo(list.get(0).getUniversity(),list.get(0).getLogo(), list.get(0).getType());
                                listObj.add(info);

                            }
                        });
                    }
                    constraintLayout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.putExtra("list",listObj);
                            Log.i(TAG, "onClick: "+listObj.get(0).getLogo());
                            intent.setClass(ResultActivity.this,MoreResultActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
        BmobQuery<Score> query2 = new BmobQuery<>();
        query2.addWhereEqualTo("year", "2019");
        query2.addWhereEqualTo("type",wenlike);
        query2.addWhereEqualTo("province",location);
        query2.addWhereEqualTo("order",pici);
        query2.addWhereLessThan("aveScore", score);
        query2.addWhereGreaterThanOrEqualTo("aveScore",mid);
        query2.findObjects(new FindListener<Score>() {
            @Override
            public void done(List<Score> list, BmobException e) {
                textView2.setText(String.valueOf(list.size()));
                if(list.size()!=0){
                    final ArrayList<SchoolInfo> listObj1 = new ArrayList<SchoolInfo>();
                    for(int i = 0;i<list.size();i++){
                        BmobQuery<SchoolInfo> query3 = new BmobQuery<>();
                        query3.addWhereEqualTo("university",list.get(i).getUniversity());
                        query3.findObjects(new FindListener<SchoolInfo>() {
                            @Override
                            public void done(List<SchoolInfo> list, BmobException e) {
                                SchoolInfo info = new SchoolInfo(list.get(0).getUniversity(),list.get(0).getLogo(), list.get(0).getType());
                                listObj1.add(info);

                            }
                        });
                    }
                    constraintLayout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.putExtra("list",listObj1);
                            Log.i(TAG, "onClick: "+listObj1.get(0).getLogo());
                            intent.setClass(ResultActivity.this,MoreResultActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
        BmobQuery<Score> query4 = new BmobQuery<>();
        query4.addWhereEqualTo("year", "2019");
        query4.addWhereEqualTo("type",wenlike);
        query4.addWhereEqualTo("province",location);
        query4.addWhereEqualTo("order",pici);
        query4.addWhereLessThan("aveScore", mid);
        query4.addWhereGreaterThanOrEqualTo("aveScore",min);
        query4.findObjects(new FindListener<Score>() {
            @Override
            public void done(List<Score> list, BmobException e) {
                textView3.setText(String.valueOf(list.size()));
                if(list.size()!=0){
                    final ArrayList<SchoolInfo> listObj2 = new ArrayList<SchoolInfo>();
                    for(int i = 0;i<list.size();i++){
                        BmobQuery<SchoolInfo> query5 = new BmobQuery<>();
                        query5.addWhereEqualTo("university",list.get(i).getUniversity());
                        query5.findObjects(new FindListener<SchoolInfo>() {
                            @Override
                            public void done(List<SchoolInfo> list, BmobException e) {
                                SchoolInfo info = new SchoolInfo(list.get(0).getUniversity(),list.get(0).getLogo(), list.get(0).getType());
                                listObj2.add(info);

                            }
                        });
                    }
                    constraintLayout3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.putExtra("list",listObj2);
                            Log.i(TAG, "onClick: "+listObj2.get(0).getLogo());
                            intent.setClass(ResultActivity.this,MoreResultActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }
}
