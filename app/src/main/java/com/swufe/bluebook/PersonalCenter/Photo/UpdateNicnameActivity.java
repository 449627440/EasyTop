package com.swufe.bluebook.PersonalCenter.Photo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.swufe.bluebook.R;

public class UpdateNicnameActivity extends AppCompatActivity {

    private EditText editText;
    private ImageButton save;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nicname);

        editText=findViewById(R.id.et_updatenicname);
        save=findViewById(R.id.btn_save);

        String nicname=getIntent().getStringExtra("nicname");
        editText.setText(nicname);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("nicname_new",editText.getText().toString());
                setResult(2,intent);
                finish();
            }
        });

        toolbar=findViewById(R.id.updatenicname_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
