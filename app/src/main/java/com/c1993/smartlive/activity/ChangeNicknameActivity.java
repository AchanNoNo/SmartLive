package com.c1993.smartlive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c1993.smartlive.R;
import com.c1993.smartlive.db.SmartLiveDB;
import com.c1993.smartlive.model.User;

/**
 * Created by c1993 on 2016/5/5.
 */
public class ChangeNicknameActivity extends BaseActivity {

    private TextView tvNewNickname;
    private Button btnChangeNickname;
    private boolean isFromMe=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_nickname_layout);
        final User user=(User)getIntent().getSerializableExtra("user_data");

        tvNewNickname=(TextView)findViewById(R.id.tv_newnickname);
        btnChangeNickname=(Button)findViewById(R.id.btn_change_nickname);
        btnChangeNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNickname=tvNewNickname.getText().toString();
                SmartLiveDB smartLiveDB=SmartLiveDB.getInstance(ChangeNicknameActivity.this);
                if(newNickname!=null&&!"".equals(newNickname)) {
                    if (smartLiveDB.updateUserNickname(user.getUserName(), newNickname) > 0) {
                        Toast.makeText(ChangeNicknameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangeNicknameActivity.this, MainActivity.class);
                        user.setNickName(newNickname);
                        intent.putExtra("user_data", user);
                        intent.putExtra("isFromMe",isFromMe);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ChangeNicknameActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ChangeNicknameActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
