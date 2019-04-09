package com.swufe.bluebook.Predict;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.swufe.bluebook.ContentActivity;
import com.swufe.bluebook.R;
import com.swufe.bluebook.TabPagers.SchoolInfo;

import java.util.List;

public class MoreResultActivity extends AppCompatActivity {
    private List<SchoolInfo> list;
    private Toolbar toolbar;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_result);
        toolbar=findViewById(R.id.MoreToolbar);
        gridView = findViewById(R.id.gridview1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        list = (List<SchoolInfo>) getIntent().getSerializableExtra("list");
        Log.i("More", "onCreate: "+list.get(0).getLogo());
        gridView.setAdapter(new ListAdapter(getApplicationContext(),list));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent config = new Intent();
                config.setClass(MoreResultActivity.this, ContentActivity.class);
                config.putExtra("university",list.get(i).getUniversity());
                startActivity(config);
            }
        });
    }
}
