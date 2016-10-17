package com.c1993.smartlive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c1993.smartlive.R;
import com.c1993.smartlive.db.SmartLiveDB;
import com.c1993.smartlive.model.User;

/**
 * Created by c1993 on 2016/5/5.
 */
public class ChangePasswordActivity extends BaseActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etNewPassword;
    private Button btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);
        final User user=(User)getIntent().getSerializableExtra("user_data");

        etUsername=(EditText)findViewById(R.id.et_username);
        etPassword=(EditText)findViewById(R.id.et_password);
        etNewPassword=(EditText)findViewById(R.id.et_newPassword);
        btnChangePassword=(Button)findViewById(R.id.btn_change_password);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();
                String newPassword=etNewPassword.getText().toString();
                SmartLiveDB smartLiveDB=SmartLiveDB.getInstance(ChangePasswordActivity.this);
                if(username==null||username.length()<=0||password==null
                        ||password.length()<=0||newPassword==null||newPassword.length()<=0){
                    Toast.makeText(ChangePasswordActivity.this,"所有选项不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    if (smartLiveDB.updateUserPasswrod(username, password, newPassword) > 0) {
                        Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        user.setPassword(newPassword);
                        Intent intent=new Intent(ChangePasswordActivity.this,MainActivity.class);
                        intent.putExtra("user_data",user);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(ChangePasswordActivity.this,"修改失败，请核对后在修改",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
