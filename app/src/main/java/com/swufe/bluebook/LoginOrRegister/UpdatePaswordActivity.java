package com.swufe.bluebook.LoginOrRegister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.swufe.bluebook.PersonalCenter.Photo.UpdateNicnameActivity;
import com.swufe.bluebook.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UpdatePaswordActivity extends AppCompatActivity {

    private EditText originalPassword;
    private EditText newPassword;
    private EditText newPasswordAgain;
    private String original;
    private String newpwd;
    private String newpwdAgain;
    private ImageButton save;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pasword);

        originalPassword=findViewById(R.id.et_original_password);
        newPassword=findViewById(R.id.et_new_password);
        newPasswordAgain=findViewById(R.id.et_new_password_again);
        save =findViewById(R.id.btn_save_updatepassword);
        toolbar=findViewById(R.id.updatepassword_toolbar);


        original=originalPassword.getText().toString();
        newpwd=newPassword.getText().toString();
        newpwdAgain=newPasswordAgain.getText().toString();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newpwd.equals(newpwdAgain)) {
                    BmobUser.updateCurrentUserPassword(original, newpwd, new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "密码修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                                try {
                                    wait(3000);
                                    Intent intent = new Intent(UpdatePaswordActivity.this, LoginActivity.class);
                                    intent.putExtra("lastname", BmobUser.getCurrentUser().getMobilePhoneNumber());
                                    startActivity(intent);
                                    finish();
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }
            }
        });
    };
}












