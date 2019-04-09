package com.swufe.bluebook.Advise;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.swufe.bluebook.R;

public class FeedbackActivity extends AppCompatActivity {
    Button btn;
    EditText editText;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //toolbar返回功能
        toolbar=findViewById(R.id.toolbar_feedback);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn=findViewById(R.id.btn_feedback);
        editText=findViewById(R.id.edit_feedback);
        //监听button事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(FeedbackActivity.this);
                builder.setMessage("感谢您的反馈，我们已经收到！" ) ;
                builder.setPositiveButton("确认" ,  null );
                builder.show();
                editText.getText().clear();
            }
        });
    }

}
