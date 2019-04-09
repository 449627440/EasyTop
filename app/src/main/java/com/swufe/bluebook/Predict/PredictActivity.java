package com.swufe.bluebook.Predict;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.swufe.bluebook.Backstage.User;
import com.swufe.bluebook.JumpPage.ScrollingActivity;
import com.swufe.bluebook.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PredictActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";
    private ImageView imageView;
    private TextView syd_tv;
    private TextView wlk_tv;
    private TextView luqupici;
    private Spinner lqmc_sp;
    private TextView kf_tv;
    private EditText syd_et;
    private EditText kf_et;
    private Spinner wlk_sp;
    private Toolbar toolbar;
    private TextView textView,textView1;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
        textView = findViewById(R.id.wenli);
        textView1 = findViewById(R.id.pici);
        imageView = findViewById(R.id.header);
        syd_tv=findViewById(R.id.shengyuandi);
        syd_et=findViewById(R.id.shengyuandi_et);
        wlk_sp=findViewById(R.id.spinner2);
        wlk_tv=findViewById(R.id.wenlike);
        kf_et=findViewById(R.id.kaofen_et);
        kf_tv=findViewById(R.id.kaofen);
        luqupici=findViewById(R.id.luqupici);
        button = findViewById(R.id.button);
        toolbar = findViewById(R.id.predict_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.bringToFront();


        final String[] ctype = new String[]{"第一批", "第二批"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        Spinner lqmc_sp = super.findViewById(R.id.spinner);
        lqmc_sp.setAdapter(adapter);

        lqmc_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textView1.setText(ctype[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final String[] ctype2 = new String[]{"文科", "理科"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype2);  //创建一个数组适配器
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        Spinner wlk_sp= super.findViewById(R.id.spinner2);
        wlk_sp.setAdapter(adapter2);
        wlk_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText(ctype2[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: "+syd_et.getText());
                Log.i(TAG, "onClick: "+textView.getText());
                if(!syd_et.getText().toString().equals("")&&!kf_et.getText().toString().equals("")&&!textView.getText().toString().equals("")&&!textView1.getText().toString().equals("")){
                    User user = new User();
                    user.setLocation(syd_et.getText().toString());
                    Log.i(TAG, "onClick: "+syd_et.getText().toString());
                    user.setScore(kf_et.getText().toString());
                    Log.i(TAG, "onClick: "+kf_et.getText().toString());
                    user.setWenlike(textView.getText().toString());
                    Log.i(TAG, "onClick: "+textView.getText().toString());
                    user.setPici(textView1.getText().toString());
                    Log.i(TAG, "onClick: "+textView1.getText().toString());
                    BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                    user.update(bmobUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.i(TAG, "done: chenggong");
                                Intent intent = new Intent();
                                intent.setClass(PredictActivity.this,ResultActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Log.i(TAG, "done: "+e);
                            }
                        }
                    });
                }else {
                    Toast.makeText(PredictActivity.this,"请检查内容是否填写完整！",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
