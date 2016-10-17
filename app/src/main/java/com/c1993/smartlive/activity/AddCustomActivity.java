package com.c1993.smartlive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c1993.smartlive.R;
import com.c1993.smartlive.db.SmartLiveDB;
import com.c1993.smartlive.model.Custom;
import com.c1993.smartlive.model.User;

/**
 * Created by c1993 on 2016/5/6.
 */
public class AddCustomActivity extends BaseActivity {
    private EditText etAddCutsom;
    private Button btnAddCustom;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_layout);
        user=(User)getIntent().getSerializableExtra("user_data");
        final boolean isFromCustom=true;

        etAddCutsom=(EditText)findViewById(R.id.et_add_custom);
        btnAddCustom=(Button)findViewById(R.id.btn_add_custom);

        btnAddCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/5/6 添加插入数据逻辑
                Custom custom=new Custom();
                custom.setC_name(etAddCutsom.getText().toString());
                custom.setC_time(0);
                custom.setU_name(user.getUserName());

                SmartLiveDB smartLiveDB=SmartLiveDB.getInstance(AddCustomActivity.this);
                smartLiveDB.insertCustom(custom);
                Toast.makeText(AddCustomActivity.this,"插入成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddCustomActivity.this,MainActivity.class);
                intent.putExtra("user_data",user);
                intent.putExtra("isFromCustom",isFromCustom);
                startActivity(intent);
                finish();
            }
        });
    }
}
