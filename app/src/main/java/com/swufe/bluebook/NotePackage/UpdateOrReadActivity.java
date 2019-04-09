package com.swufe.bluebook.NotePackage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;

import com.swufe.bluebook.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateOrReadActivity extends Activity {

    /**
     * 布局控件
     */
    private ImageButton mComplete;
    private Toolbar toolbar;
    private EditText mContent;
    private LinearLayout mScreen;
    /**
     * 备忘录数据
     */
    private int noteId;
    private String noteTime;
    private String noteContent;
    private String originData;
    /**
     * 数据库
     */
    private NoteDB mNoteDB;
    private static Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.write_note);
        /**
         * 获取传递过来的note对象
         */
        Intent intent = getIntent();
        // 传递Note对象，必须要Note实体实现Serializable
//        note = (Note) intent.getSerializableExtra("note_item");
        noteId = intent.getIntExtra("note_id",0);
        Log.d("Anonymous", "传递后的备忘录ID:" + noteId);

        initView();
        /**
         * 加载显示数据
         */
        new LoadAsyncTask().execute();
        initEvent();
    }

    private void initView() {
        /**
         * 布局控件初始化
         */
        mComplete = (ImageButton) findViewById(R.id.note_save);
        mContent = (EditText) findViewById(R.id.note_content);
        mScreen = (LinearLayout) findViewById(R.id.screen_view);
        /**
         * 获取数据库实例
         */
        mNoteDB = NoteDB.getInstance(this);
        toolbar=findViewById(R.id.writenote_toolbar);
    }

    private void initEvent() {
        /**
         * 返回上一级菜单，直接销毁当前活动
         */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataOrNot();
            }
        });

        /**
         * 完成按钮，修改备忘录到数据库
         */
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContent.getText().toString().trim().equals("")){
//                    Log.d("Anonymous","进入判断为空函数");
                    new DeleteAsyncTask(mNoteDB).execute(noteId);
                    finish();
                } else if (mContent.getText().toString().equals(originData)) {
                    finish();
                } else {
//                    Log.d("Anonymous","进入判断不为空函数");
                    new UpdateAsyncTask().execute();
//                    Toast.makeText(UpdateOrReadActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        /**
         * 点击屏幕空白区域，EditText选中
         */


    }

    /**
     * 根据id从数据库读数据的异步任务
     */
    class LoadAsyncTask extends AsyncTask<Void,Void,Note> {

        @Override
        protected Note doInBackground(Void... voids) {
            note = mNoteDB.loadById(noteId);
            return note;
        }

        @Override
        protected void onPostExecute(Note note) {
            super.onPostExecute(note);
            /**
             * 根据传递进来的Note显示备忘录内容，并把光标移动到最后
             * 记录最初的文本内容
             */
            originData = note.getContent();
            mContent.setText(note.getContent());
            mContent.setSelection(mContent.getText().toString().length());
        }
    }
    /**
     * 更新数据库的异步任务
     */
    class UpdateAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * 记录数据
             */
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            Date date = new Date(System.currentTimeMillis());
            noteTime = sdf.format(date);
            noteContent = mContent.getText().toString();
            note.setTime(noteTime);
            note.setContent(noteContent);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDB.updateById(noteTime, noteContent, noteId);
            return null;
        }
    }
    /**
     * 根据是否有内容，提示保存
     */
    private void updateDataOrNot() {
        if (!mContent.getText().toString().equals(originData)) {
            new AlertDialog.Builder(UpdateOrReadActivity.this)
                    .setTitle("提示")
                    .setMessage("需要保存您编辑的内容吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new UpdateAsyncTask().execute();
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        } else {
            finish();
        }
    }
    /**
     * 返回键事件
     * 根据内容是否有变化，提示是否保存
     */
    @Override
    public void onBackPressed() {
        updateDataOrNot();
    }
}
