package com.swufe.bluebook.LoginOrRegister;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jkb.vcedittext.VerificationCodeEditText;
import com.swufe.bluebook.Backstage.User;
import com.swufe.bluebook.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText phone;
    private EditText password;
    private VerificationCodeEditText code;
    private Button send;
    private Button register;
    private String sphone = " ";
    private String spassword = " ";
    private String scode = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        code = findViewById(R.id.code);

        send = findViewById(R.id.send);
        send.setOnClickListener(this);
        register = findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send:
                sphone = phone.getText().toString();
                if(!sphone.equals("") && sphone.length() == 11){
                    BmobSMS.requestSMSCode(sphone, "诚信状", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if(e == null){
                                Toast.makeText(getApplicationContext(),"短信验证码已发送",Toast.LENGTH_SHORT).show();
                                send.setEnabled(false);
                                new CountDownTimer(60000,1000){
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        send.setText(millisUntilFinished / 1000 + "秒");
                                    }
                                    @Override
                                    public void onFinish() {
                                        send.setEnabled(true);
                                        send.setText("重新发送");
                                    }
                                }.start();
                            }
                            else
                                Toast.makeText(getApplicationContext(),"短信验证码发送失败，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(this,"手机号码格式不正确",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.register:
                spassword = password.getText().toString();
                scode = code.getText().toString();
                if(!spassword.equals("") && !scode.equals("")){
                    User user = new User();
                    user.setUsername(sphone);
                    user.setMobilePhoneNumber(sphone);//设置手机号码（必填）
                    user.setPassword(spassword);
                    user.setNickname("城事");
                    user.setHeadPortrait("http://bmob-cdn-21576.b0.upaiyun.com/2018/09/22/f0143a5440f171ee8047f13e5fd528c7.png");
                    user.signOrLogin(scode, new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if(e == null){
                                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent();
                                        intent.putExtra("phone",sphone);
                                        intent.putExtra("pwd",spassword);
                                        setResult(2, intent);
                                        finish();
                                    }
                                }, 1000);//1.5秒后执行Runnable中的run方法
                            }else{
                                Toast.makeText(getApplicationContext(),"短信验证码错误或已注册",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this,"请把内容填写完整",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}

