package com.c1993.smartlive.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.c1993.smartlive.R;
import com.c1993.smartlive.adapter.CustomAdapter;
import com.c1993.smartlive.adapter.ScheduleAdapter;
import com.c1993.smartlive.db.SmartLiveDB;
import com.c1993.smartlive.model.Custom;
import com.c1993.smartlive.model.Schedule;
import com.c1993.smartlive.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by c1993 on 2016/5/3.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Button btnSchduleNormal;
    private Button btnSchduleChoose;
    private Button btnCustomNormal;
    private Button btnCustomChoose;
    private Button btnDiscoverNormal;
    private Button btnDiscoverChoose;
    private Button btnMeNormal;
    private Button btnMechoose;
    private TextView tvTitle;

    private LinearLayout scheduleLayout;
    private LinearLayout customLayout;
    private LinearLayout discoverLayout;
    private LinearLayout meLayout;
    private LinearLayout weatherLayout;
    private LinearLayout mapLayout;

    private TextView tvUsernickname;
    private TextView tvChangenickname;
    private TextView tvChangePassword;
    private TextView tvChangeAccount;
    private TextView tvAbout;
    private TextView tvSetting;
    private Button btnExit;
    private ImageView ivScheduleAdd;
    private ImageView ivCustomAdd;

    private List<Schedule> scheduleList=new ArrayList<Schedule>();
    private List<Custom> customList=new ArrayList<Custom>();
    private ListView lvSchedule;
    private ListView lvCustom;

    private User user;
    private static boolean mBackKeyPressed=false;

    private boolean isFromCustom=false;
    private boolean isFromMe=false;

    final SmartLiveDB smartLiveDB=SmartLiveDB.getInstance(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_main);

        user=(User)getIntent().getSerializableExtra("user_data");
        isFromCustom=getIntent().getBooleanExtra("isFromCustom",false);
        isFromMe=getIntent().getBooleanExtra("isFromMe",false);

        tvTitle=(TextView)findViewById(R.id.title_tv);
        btnSchduleNormal=(Button)findViewById(R.id.btn_schedule_normal);
        btnSchduleChoose=(Button)findViewById(R.id.btn_schedule_choose);
        btnCustomNormal=(Button)findViewById(R.id.btn_custom_normal);
        btnCustomChoose=(Button)findViewById(R.id.btn_custom_choose);
        btnDiscoverNormal=(Button)findViewById(R.id.btn_discover_normal);
        btnDiscoverChoose=(Button)findViewById(R.id.btn_discover_choose);
        btnMeNormal=(Button)findViewById(R.id.btn_me_normal);
        btnMechoose=(Button)findViewById(R.id.btn_me_choose);
        scheduleLayout=(LinearLayout)findViewById(R.id.schedule_layout);
        customLayout=(LinearLayout)findViewById(R.id.custom_layout);
        discoverLayout=(LinearLayout)findViewById(R.id.discover_layout);
        meLayout=(LinearLayout)findViewById(R.id.me_layout);
        weatherLayout=(LinearLayout)findViewById(R.id.weather_layout);
        mapLayout=(LinearLayout)findViewById(R.id.map_layout);

        tvUsernickname=(TextView)findViewById(R.id.user_nickname);
        tvChangenickname=(TextView)findViewById(R.id.change_nickname);
        tvChangeAccount=(TextView)findViewById(R.id.change_account) ;
        tvChangePassword=(TextView)findViewById(R.id.change_password);
        tvAbout=(TextView)findViewById(R.id.about);
        tvSetting=(TextView)findViewById(R.id.setting);
        btnExit=(Button)findViewById(R.id.btn_exit);
        ivScheduleAdd=(ImageView)findViewById(R.id.schedule_add);
        ivCustomAdd=(ImageView)findViewById(R.id.custom_add);

        lvSchedule=(ListView)findViewById(R.id.lv_schedule);
        lvCustom=(ListView)findViewById(R.id.lv_custom);

        tvUsernickname.setText(user.getNickName());
        tvChangenickname.setOnClickListener(this);
        tvChangeAccount.setOnClickListener(this);
        tvChangePassword.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        ivScheduleAdd.setOnClickListener(this);
        ivCustomAdd.setOnClickListener(this);
        weatherLayout.setOnClickListener(this);
        mapLayout.setOnClickListener(this);


        btnSchduleNormal.setOnClickListener(this);
        btnCustomNormal.setOnClickListener(this);
        btnDiscoverNormal.setOnClickListener(this);
        btnMeNormal.setOnClickListener(this);

        showSchedule();
        if(isFromCustom){
            showCustom();
            isFromCustom=false;
        }
        if(isFromMe){
            showMe();
            isFromMe=false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_schedule_normal:
                showSchedule();
                break;
            case R.id.btn_custom_normal:
                showCustom();
                break;
            case R.id.btn_discover_normal:
                showDiscover();
                break;
            case R.id.btn_me_normal:
                showMe();
                break;
            case R.id.change_nickname:
                Intent changeNichnameIntent=new Intent(MainActivity.this,ChangeNicknameActivity.class);
                changeNichnameIntent.putExtra("user_data",user);
                startActivity(changeNichnameIntent);
                break;
            case R.id.change_account:
                Intent changeAccountIntent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(changeAccountIntent);
                finish();
                break;
            case R.id.change_password:
                Intent changePasswordIntent=new Intent(MainActivity.this,ChangePasswordActivity.class);
                changePasswordIntent.putExtra("user_data",user);
                startActivity(changePasswordIntent);
                break;
            case R.id.btn_exit:
                AlertDialog.Builder exitDialog=new AlertDialog.Builder(MainActivity.this);
                exitDialog.setTitle("退出");
                exitDialog.setMessage("确定要退出吗？");
                exitDialog.setCancelable(true);
                exitDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                exitDialog.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                    }
                });
                exitDialog.show();
                break;
            case R.id.about:
                AlertDialog.Builder aboutDialog=new AlertDialog.Builder(MainActivity.this);
                aboutDialog.setTitle("About");
                aboutDialog.setMessage("By:yuchuan cheng");
                aboutDialog.setCancelable(false);
                aboutDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aboutDialog.show();
                break;
            case R.id.setting:
                Intent settingIntent=new Intent(MainActivity.this,SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.schedule_add:
                Intent addScheduleIntent=new Intent(MainActivity.this,AddScheduleActivity.class);
                addScheduleIntent.putExtra("user_data",user);
                startActivity(addScheduleIntent);
                break;

            case R.id.custom_add:
                Intent addCustomIntent=new Intent(MainActivity.this,AddCustomActivity.class);
                addCustomIntent.putExtra("user_data",user);
                startActivity(addCustomIntent);
                break;
            case R.id.map_layout:
                Intent mapIntent=new Intent(MainActivity.this,MapActivity.class);
                startActivity(mapIntent);
                break;
            case R.id.weather_layout:
                Intent weatherIntent=new Intent(MainActivity.this,WeatherActivity.class);
                startActivity(weatherIntent);
                break;

        }
    }

    protected void showSchedule(){
        btnSchduleNormal.setVisibility(View.INVISIBLE);
        btnCustomNormal.setVisibility(View.VISIBLE);
        btnDiscoverNormal.setVisibility(View.VISIBLE);
        btnMeNormal.setVisibility(View.VISIBLE);

        btnSchduleChoose.setVisibility(View.VISIBLE);
        btnCustomChoose.setVisibility(View.INVISIBLE);
        btnDiscoverChoose.setVisibility(View.INVISIBLE);
        btnMechoose.setVisibility(View.INVISIBLE);

        scheduleLayout.setVisibility(View.VISIBLE);
        customLayout.setVisibility(View.GONE);
        discoverLayout.setVisibility(View.GONE);
        meLayout.setVisibility(View.GONE);

        ivScheduleAdd.setVisibility(View.VISIBLE);
        ivCustomAdd.setVisibility(View.GONE);

        tvTitle.setText("日程");

        scheduleList=smartLiveDB.querySchedule(user.getUserName());
        ScheduleAdapter adapter=new ScheduleAdapter(MainActivity.this, R.layout.schedule_item,scheduleList);
        lvSchedule.setAdapter(adapter);
        lvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Schedule schedule=scheduleList.get(position);
                Intent changeScheduleIntent=new Intent(MainActivity.this,ChangeScheduleActivity.class);
                changeScheduleIntent.putExtra("schedule_data",schedule);
                changeScheduleIntent.putExtra("user_data",user);
                startActivity(changeScheduleIntent);
            }
        });
        //设置长按删除功能
        lvSchedule.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Schedule schedule=scheduleList.get(position);
                AlertDialog.Builder scheduleDialog=new AlertDialog.Builder(MainActivity.this);
                scheduleDialog.setTitle("删除任务");
                scheduleDialog.setMessage("确定要删除吗？");
                scheduleDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                scheduleDialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        smartLiveDB.deleteSchedule(schedule.getS_name(),user.getUserName());
                        showSchedule();
                    }
                });
                scheduleDialog.show();
                return true;
            }
        });

    }

    protected void showCustom(){
        btnSchduleNormal.setVisibility(View.VISIBLE);
        btnCustomNormal.setVisibility(View.INVISIBLE);
        btnDiscoverNormal.setVisibility(View.VISIBLE);
        btnMeNormal.setVisibility(View.VISIBLE);

        btnSchduleChoose.setVisibility(View.INVISIBLE);
        btnCustomChoose.setVisibility(View.VISIBLE);
        btnDiscoverChoose.setVisibility(View.INVISIBLE);
        btnMechoose.setVisibility(View.INVISIBLE);

        scheduleLayout.setVisibility(View.GONE);
        customLayout.setVisibility(View.VISIBLE);
        discoverLayout.setVisibility(View.GONE);
        meLayout.setVisibility(View.GONE);

        ivScheduleAdd.setVisibility(View.GONE);
        ivCustomAdd.setVisibility(View.VISIBLE);

        tvTitle.setText("习惯");

        customList=smartLiveDB.queryCustom(user.getUserName());
        CustomAdapter adapter=new CustomAdapter(MainActivity.this,R.layout.custom_item,customList);
        lvCustom.setAdapter(adapter);
        lvCustom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Custom custom=customList.get(position);
                AlertDialog.Builder customDialog=new AlertDialog.Builder(MainActivity.this);
                customDialog.setTitle("完成");
                customDialog.setMessage("确定完成了吗？每天完成后才可点击。");
                customDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                customDialog.setPositiveButton("完成", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        smartLiveDB.updateCustom(custom.getC_name());
                        showCustom();
                    }
                });
                customDialog.show();
            }
        });

    }

    protected void showDiscover(){
        btnSchduleNormal.setVisibility(View.VISIBLE);
        btnCustomNormal.setVisibility(View.VISIBLE);
        btnDiscoverNormal.setVisibility(View.INVISIBLE);
        btnMeNormal.setVisibility(View.VISIBLE);

        btnSchduleChoose.setVisibility(View.INVISIBLE);
        btnCustomChoose.setVisibility(View.INVISIBLE);
        btnDiscoverChoose.setVisibility(View.VISIBLE);
        btnMechoose.setVisibility(View.INVISIBLE);

        scheduleLayout.setVisibility(View.GONE);
        customLayout.setVisibility(View.GONE);
        discoverLayout.setVisibility(View.VISIBLE);
        meLayout.setVisibility(View.GONE);

        ivScheduleAdd.setVisibility(View.GONE);
        ivCustomAdd.setVisibility(View.GONE);

        tvTitle.setText("发现");

    }

    protected void showMe(){
        btnSchduleNormal.setVisibility(View.VISIBLE);
        btnCustomNormal.setVisibility(View.VISIBLE);
        btnDiscoverNormal.setVisibility(View.VISIBLE);
        btnMeNormal.setVisibility(View.INVISIBLE);

        btnSchduleChoose.setVisibility(View.INVISIBLE);
        btnCustomChoose.setVisibility(View.INVISIBLE);
        btnDiscoverChoose.setVisibility(View.INVISIBLE);
        btnMechoose.setVisibility(View.VISIBLE);

        scheduleLayout.setVisibility(View.GONE);
        customLayout.setVisibility(View.GONE);
        discoverLayout.setVisibility(View.GONE);
        meLayout.setVisibility(View.VISIBLE);

        ivScheduleAdd.setVisibility(View.GONE);
        ivCustomAdd.setVisibility(View.GONE);

        tvTitle.setText("我");

    }

    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed){
            Toast.makeText(MainActivity.this,"再按一下退出",Toast.LENGTH_SHORT).show();
            mBackKeyPressed=true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mBackKeyPressed=false;//超过2秒就擦除第一次按键
                }
            },2000);
        }else {
            //退出程序
            this.finish();
            System.exit(0);
        }
    }
}
