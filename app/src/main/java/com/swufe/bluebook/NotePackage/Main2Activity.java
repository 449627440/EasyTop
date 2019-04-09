package com.swufe.bluebook.NotePackage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.swufe.bluebook.R;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends Activity {
    /**
     * 布局控件
     */
    private TextView mTitle;
    private TextView mNoteNum;
    private ImageButton mWrite;
    private ListView mNoteListView;
    private ImageButton mAbout;
    private Toolbar toolbar;
    /**
     * 数据库实例，数据源
     */
    private List<Note> mNoteList = new ArrayList<Note>() ;
    private NoteDB mNoteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);

        initView();
        new NewAsyncTask().execute();
        initEvent();

    }

    private void initEvent() {
        /**
         * 新写一条备忘录
         */
        mWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 修改或查看一条已有的备忘录
         */
        mNoteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                Note note = (Note) adapterView.getItemAtPosition(i);
                Log.i("Anonymous", "点击ListView获取的note id: " + note.getId());
                Intent intent = new Intent(Main2Activity.this, UpdateOrReadActivity.class);
                intent.putExtra("note_id", note.getId());
                startActivity(intent);
            }
        });
        /**
         * listview长按删除
         */
        mNoteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                final Note note = (Note) parent.getItemAtPosition(position);
//                Log.d("Anonymous", "长按ListView获取的note id: " + note.getId());
                /**
                 * 长按提示是否删除
                 */
                new AlertDialog.Builder(Main2Activity.this)
                        .setTitle("提示")
                        .setMessage("真的要删除这条记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new DeleteAsyncTask(mNoteDB).execute(note.getId());
                                new NewAsyncTask().execute();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return true;
            }
        });
        /**
         * 关于自己
         */



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void initView() {
        /**
         * 布局控件初始化
         */

//        mTitle.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mNoteNum = (TextView) findViewById(R.id.note_num);
        mWrite = (ImageButton) findViewById(R.id.btn_addnote);
        mNoteListView = (ListView) findViewById(R.id.listview);
        /**
         * 获取数据库实例
         */
        mNoteDB = NoteDB.getInstance(this);
        toolbar = findViewById(R.id.note_toolbar);
    }
    /**
     * 异步加载备忘录
     */
    class NewAsyncTask extends AsyncTask<Void,Void,List<Note>> {

        @Override
        protected List<Note> doInBackground(Void... voids) {
            mNoteList = mNoteDB.loadNotes();
            return mNoteList;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);
            /**
             * 设置适配器，绑定适配器
             */
            MyAdapter myAdapter = new MyAdapter(Main2Activity.this,notes,mNoteListView);
            mNoteListView.setAdapter(myAdapter);
            /**
             * 更新备忘录记录数
             */
            int temp = mNoteList.size();
            mNoteNum.setText("共 " + temp + " 条");
        }
    }
    /**
     * 当活动恢复时，刷新listview和备忘录记录数
     */
    @Override
    protected void onResume() {
        super.onResume();
        new NewAsyncTask().execute();
    }

}
