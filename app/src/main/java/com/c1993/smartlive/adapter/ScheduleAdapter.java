package com.c1993.smartlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.c1993.smartlive.R;
import com.c1993.smartlive.model.Schedule;

import java.util.Date;
import java.util.List;

/**
 * Created by c1993 on 2016/5/12.
 */
public class ScheduleAdapter extends ArrayAdapter<Schedule> {
    private int resourceId;
    public ScheduleAdapter(Context context, int textViewResourceId, List<Schedule> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Schedule schedule=getItem(position);    //获取当前项的Schedule实例
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView ScheduleDate=(TextView)view.findViewById(R.id.schedule_date);
        TextView ScheduleName=(TextView)view.findViewById(R.id.schedule_name);
        ScheduleName.setText(schedule.getS_name());
        Date date=new Date(Long.valueOf(schedule.getS_time()));

        //分割时间字符串 星期-月-日-时间-时区-年
        String[] array=date.toString().split(" ");
        switch (array[1]){
            case "Jan":array[1]="1";break;
            case "Feb":array[1]="2";break;
            case "Mar":array[1]="3";break;
            case "Apr":array[1]="4";break;
            case "May":array[1]="5";break;
            case "Jun":array[1]="6";break;
            case "Jul":array[1]="7";break;
            case "Aug":array[1]="8";break;
            case "Sep":array[1]="9";break;
            case "Oct":array[1]="10";break;
            case "Nov":array[1]="11";break;
            case "Dec":array[1]="12";break;
        }
        //去掉秒钟
        String[] temp=array[3].split(":");
        String hour=temp[0]+":"+temp[1];
        String time=array[5]+"-"+array[1]+"-"+array[2]+"   "+hour;
        ScheduleDate.setText(time);

        return view;
    }
}
