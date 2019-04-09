package com.swufe.bluebook.Collect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.swufe.bluebook.JumpPage.ScrollingActivity;
import com.swufe.bluebook.R;

import java.util.ArrayList;

public class CollectActivity extends AppCompatActivity {

    private String TAG="MyCollectionActivity";
    private ArrayList<MyCollection> myCollectionList=new ArrayList<MyCollection>();
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        initMyCollection(); // 初始化数据
        final MyCollectionAdapter adapter = new MyCollectionAdapter(this,
                R.layout.item_collection, myCollectionList);
        ListView listView = findViewById(R.id.myCollectionList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //对整个item设置点击事件
                MyCollection collection=myCollectionList.get(position);
                Toast.makeText(CollectActivity.this,collection.getTitle_collection(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(CollectActivity.this,ScrollingActivity.class);
                startActivity(intent);
            }
        });
        //listView长按事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(CollectActivity.this);
                builder.setMessage("确定删除?");
                builder.setTitle("提示");

                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(myCollectionList.remove(position)!=null){
                            //向后台提出删除申请

                            Log.i(TAG, "onClick: 删除成功！");
                        }else {
                            Log.i(TAG, "onClick: 删除失败！");
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "删除列表项", Toast.LENGTH_SHORT).show();
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                builder.create().show();
                return true;
            }
        });


        //toolbar返回功能
        toolbar=findViewById(R.id.toolbar_collection);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initMyCollection(){
        //从后台获取所有数据
        MyCollection collection1= new MyCollection("http://bmob-cdn-21576.b0.upaiyun.com/2018/10/05/4f103ca740775f9280283c8002dab2ab.png","上海|别样的流连忘返");
        myCollectionList.add(collection1);
    }

}