package com.c1993.smartlive.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c1993.smartlive.R;
import com.c1993.smartlive.db.SmartLiveDB;
import com.c1993.smartlive.db.SmartLiveOpenHelper;
import com.c1993.smartlive.model.User;

/**
 * Created by c1993 on 2016/5/3.
 */
public class RegisterActivity extends BaseActivity {

    private SmartLiveDB smartLiveDB;
    private Button registerBtn;
    private EditText usernameEt;
    private EditText passwordEt;
    private EditText nicknameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        registerBtn=(Button)findViewById(R.id.btn_register);
        usernameEt=(EditText)findViewById(R.id.et_username);
        passwordEt=(EditText)findViewById(R.id.et_password);
        nicknameEt=(EditText)findViewById(R.id.et_nickname);




        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartLiveDB=SmartLiveDB.getInstance(RegisterActivity.this);
                final User user=new User();
                String username=usernameEt.getText().toString();
                String password=passwordEt.getText().toString();
                String nickname=nicknameEt.getText().toString();
                user.setUserName(username);
                user.setPassword(password);
                user.setNickName(nickname);
                //判断注册条件是否为空
                if(username==null||username.length()<=0||password==null||password.length()<=0
                        ||nickname==null||nickname.length()<=0){
                    Toast.makeText(RegisterActivity.this,"所有选项均不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    //验证用户名是否已经被注册过
                    if(!smartLiveDB.checkUsername(username)) {
                        smartLiveDB.saveUser(user);
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this,"用户名已被注册，更改一个吧。",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
