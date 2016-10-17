package com.c1993.smartlive.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by c1993 on 2016/5/3.
 */
public class SmartLiveOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER="create table User ("
            +"id integer primary key autoincrement, "
            +"username text, "
            +"password text, "
            +"nickname text)";

    public static final String CREATE_CUSTOM="create table Custom ("
            +"id integer primary key autoincrement, "
            +"c_name text, "
            +"c_time integer, "
            +"u_name text)";

    public static final String CREATE_SCHEDULE="create table Schedule ("
            +"id integer primary key autoincrement, "
            +"s_name text, "
            +"s_time text, "
            +"u_name text)";

    public SmartLiveOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);//创建User表
        db.execSQL(CREATE_CUSTOM);//创建Custom表
        db.execSQL(CREATE_SCHEDULE);//创建Schedule表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
