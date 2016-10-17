package com.c1993.smartlive.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.c1993.smartlive.model.Custom;
import com.c1993.smartlive.model.Schedule;
import com.c1993.smartlive.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1993 on 2016/5/3.
 */
public class SmartLiveDB {
    public static final String DB_NAME="smartlive";
    public static final int VERSION=1;
    private static SmartLiveDB smartLiveDB;
    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private SmartLiveDB(Context context){
        SmartLiveOpenHelper dbHelper=new SmartLiveOpenHelper(context,DB_NAME,null,VERSION);
        db=dbHelper.getWritableDatabase();
    }

    /**
     * 获取SmartliveDB的实例
     */
    public synchronized static SmartLiveDB getInstance(Context context){
        if(smartLiveDB==null){
            smartLiveDB=new SmartLiveDB(context);
        }
        return smartLiveDB;
    }

    /**
     * 存储user数据
     */
    public void saveUser(User user){
        if(user!=null){
            ContentValues values=new ContentValues();
            values.put("username",user.getUserName());
            values.put("password",user.getPassword());
            values.put("nickname",user.getNickName());
            db.insert("User",null,values);
        }
    }

    public boolean login(String username,String password){
        String sql="select * from User where username=? and password=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username,password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }else {
            return false;
        }
    }

    public boolean checkUsername(String username){
        String sql="select * from User where username=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }else {
            return false;
        }
    }

    public User queryUser(String username){
        String sql="select * from User where username=?";
        Cursor cursor=db.rawQuery(sql,new String[]{username});
        User user=new User();
        if(cursor.moveToFirst()==true){
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUserName(cursor.getString(cursor.getColumnIndex("username")));
            user.setNickName(cursor.getString(cursor.getColumnIndex("nickname")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
        }
        if(cursor==null){
            cursor.close();
        }
        return user;
    }

    public int updateUserNickname(String username,String newNickname){
        ContentValues values=new ContentValues();
        values.put("nickname",newNickname);

        if(db.update("User",values,"username=?",new String[]{username})>0){
            return 1;
        }else {
            return 0;
        }

    }

    public int updateUserPasswrod(String username,String password,String newPassword){
        ContentValues values=new ContentValues();
        values.put("password",newPassword);
        if(db.update("User",values,"username=? and password=?",new String[]{username,password})>0){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 以下是Custom表的方法
     */
    public void insertCustom(Custom custom){
        if(custom!=null){
            ContentValues values=new ContentValues();
            values.put("c_name",custom.getC_name());
            values.put("c_time",custom.getC_time());
            values.put("u_name",custom.getU_name());
            db.insert("Custom",null,values);
        }
    }

    public List<Custom> queryCustom(String u_name){
        String sql="select * from Custom where u_name=?";
        Cursor cursor=db.rawQuery(sql,new String[]{u_name});
        List<Custom> list=new ArrayList<Custom>();
        if(cursor.moveToFirst()){
            do {
                Custom custom=new Custom();
                custom.setC_name(cursor.getString(cursor.getColumnIndex("c_name")));
                custom.setC_time(cursor.getInt(cursor.getColumnIndex("c_time")));
                list.add(custom);
            }while (cursor.moveToNext());
        }
        if(cursor==null){
            cursor.close();
        }
        return list;
    }

    public void updateCustom(String c_name){
        String sql="select c_time from Custom where c_name=?";
        Cursor cursor=db.rawQuery(sql,new String[]{c_name});
        if(cursor.moveToFirst()){
            int time=cursor.getInt(cursor.getColumnIndex("c_time"));
            time++;
            ContentValues values=new ContentValues();
            values.put("c_time",time);
            db.update("Custom",values,"c_name=?",new String[]{c_name});
        }
    }

    /**
     * 以下是Schedule表的方法
     */
    public void insertSchedule(Schedule schedule){
        if(schedule!=null){
            ContentValues values=new ContentValues();
            values.put("s_name",schedule.getS_name());
            values.put("s_time",schedule.getS_time());
            values.put("u_name",schedule.getU_name());
            db.insert("Schedule",null,values);
        }
    }

    public List<Schedule> querySchedule(String u_name){
        String sql="select * from Schedule where u_name=? order by s_time";
        Cursor cursor=db.rawQuery(sql,new String[]{u_name});
        List<Schedule> list=new ArrayList<Schedule>();
        if(cursor.moveToFirst()){
            do{
                Schedule schedule=new Schedule();
                schedule.setS_name(cursor.getString(cursor.getColumnIndex("s_name")));
                schedule.setS_time(cursor.getString(cursor.getColumnIndex("s_time")));
                schedule.setU_name(cursor.getString(cursor.getColumnIndex("u_name")));
                list.add(schedule);
            }while (cursor.moveToNext());
        }
        if (cursor==null){
            cursor.close();
        }
        return list;
    }

    public int deleteSchedule(String s_name,String u_name){
        if(db.delete("Schedule","s_name=? and u_name=?",new String[]{s_name,u_name})>0){
            return 1;
        }else {
            return 0;
        }
    }

    public void updateSchedule(String new_s_name,String new_s_time,String old_s_name,String u_name){
        ContentValues values=new ContentValues();
        values.put("s_name",new_s_name);
        values.put("s_time",new_s_time);
        db.update("Schedule",values,"s_name=? and u_name=?",new String[]{old_s_name,u_name});
    }


}
