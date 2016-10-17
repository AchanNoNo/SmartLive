package com.c1993.smartlive.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.c1993.smartlive.R;

/**
 * Created by c1993 on 2016/5/5.
 */
public class SettingActivity extends BaseActivity {
    private Button btnPhone;
    private Button btnEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        btnPhone=(Button)findViewById(R.id.phone);
        btnEmail=(Button)findViewById(R.id.email);

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:18636803706"));
                startActivity(intent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:344238796@qq.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
                intent.putExtra(Intent.EXTRA_TEXT, "这是内容");
                startActivity(intent);
            }
        });
    }
}
