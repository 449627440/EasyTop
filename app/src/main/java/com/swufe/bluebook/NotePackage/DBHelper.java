package com.swufe.bluebook.NotePackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    /**
     * 创建笔记表
     */
    private static final String CREATE_NOTE = "create table Note(" +
            "id integer primary key autoincrement," +
            "content text," +
            "time text)";

    private Context mContext;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTE);
//        Toast.makeText(mContext,"Created",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
