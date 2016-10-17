package com.c1993.smartlive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.c1993.smartlive.R;
import com.c1993.smartlive.db.SmartLiveDB;
import com.c1993.smartlive.model.Schedule;
import com.c1993.smartlive.model.User;

/**
 * Created by c1993 on 2016/5/12.
 */
public class ChangeScheduleActivity extends BaseActivity {
    private User user;
    private Schedule schedule;
    private EditText etChangeSchedule;
    private CalendarView calendarView;
    private EditText etHour;
    private EditText etMinute;
    private Button btnChangeSchedule;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_schedule_layout);

        user=(User) getIntent().getSerializableExtra("user_data");
        schedule=(Schedule)getIntent().getSerializableExtra("schedule_data");
        etChangeSchedule=(EditText)findViewById(R.id.et_change_schedule);
        calendarView=(CalendarView)findViewById(R.id.calendar);
        etHour=(EditText)findViewById(R.id.et_hour);
        etMinute=(EditText)findViewById(R.id.et_minute);
        btnChangeSchedule=(Button)findViewById(R.id.btn_change_schedule);

        etChangeSchedule.setText(schedule.getS_name());
        btnChangeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long total=24*60*60*1000; //一天的总毫秒数
                //控件获取的时间
                long get=calendarView.getDate();
                long l=0;
                //总毫秒数 减去 当天0时到现在的毫秒数（现在的毫秒数%一天的总毫秒数，取余）
                if(get%100000==0){
                    //不是今天
                    l=get;
                }else {
                    //是今天
                    l=get-(get%total)-8*60*60*1000;
                }
                long h=Long.valueOf(etHour.getText().toString());
                long m=Long.valueOf(etMinute.getText().toString());
                long s=l+(h*60+m)*60*1000;  //获取的最终时间
                //需要插入数据库中的时间字符串,以及日程名称
                date=String.valueOf(s);//将Long时间转换为字符串
                String name=etChangeSchedule.getText().toString();
                SmartLiveDB smartLiveDB=SmartLiveDB.getInstance(ChangeScheduleActivity.this);
                smartLiveDB.updateSchedule(name,date,schedule.getS_name(),user.getUserName());
                Intent intent=new Intent(ChangeScheduleActivity.this,MainActivity.class);
                intent.putExtra("user_data",user);
                startActivity(intent);
            }
        });
    }
}
