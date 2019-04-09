package com.swufe.bluebook.LoginOrRegister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.swufe.bluebook.Backstage.User;
import com.swufe.bluebook.CityChooseSlide.AddressActivity;
import com.swufe.bluebook.MainActivity;
import com.swufe.bluebook.R;

import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private String susername;
    private String spassword;
    private String lastname;
    private SharedPreferences spLoc;

    ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bmob.initialize(this, "9b90c2d2f64466ed4e8c1bef6c11099a");

        User bmobUser = BmobUser.getCurrentUser(User.class);
        if(bmobUser != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        lastname=getIntent().getStringExtra("lastname");
        if(lastname!=null){
            username.setText(lastname);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.login:
                susername = username.getText().toString();
                spassword = password.getText().toString();
                if(!susername.equals("") && !spassword.equals("")){
                    BmobUser.loginByAccount(susername, spassword, new LogInListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if(user!=null){
                                Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        spLoc = getSharedPreferences("CityChose", Context.MODE_PRIVATE);
                                        String selected= spLoc.getString("CityChose",null);
                                        if (selected == null || selected.equals("")) {
                                            Intent intent = new Intent(LoginActivity.this, AddressActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }, 1500);//1.5秒后执行Runnable中的run方法
                            }else{
                                Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
                                password.setText("");
                            }
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(),"请正确输入",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register:
                intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,2);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2){
            if(requestCode==2){
                String phone=data.getStringExtra("phone");
                String pwd=data.getStringExtra("pwd");
                Log.i("login", "onActivityResult: phone="+phone);
                username.setText(phone);
                password.setText(pwd);
            }
        }
    }
}
