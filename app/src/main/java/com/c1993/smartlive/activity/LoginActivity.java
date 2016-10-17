package com.c1993.smartlive.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c1993.smartlive.R;
import com.c1993.smartlive.db.SmartLiveDB;
import com.c1993.smartlive.model.User;

/**
 * Created by c1993 on 2016/5/3.
 */
public class LoginActivity extends BaseActivity {

    private TextView registerTv;
    private EditText usernameEt;
    private EditText passwordEt;
    private Button loginBtn;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        registerTv=(TextView)findViewById(R.id.register);
        usernameEt=(EditText)findViewById(R.id.username);
        passwordEt=(EditText)findViewById(R.id.password);
        loginBtn=(Button)findViewById(R.id.login);
        rememberPass=(CheckBox)findViewById(R.id.remember_pass);

        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember) {
            String username = pref.getString("username", "");
            String password = pref.getString("password", "");
            usernameEt.setText(username);
            passwordEt.setText(password);
            rememberPass.setChecked(true);
        }

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartLiveDB smartLiveDB=SmartLiveDB.getInstance(LoginActivity.this);
                if(smartLiveDB.login(usernameEt.getText().toString(),passwordEt.getText().toString())){
                    User user=new User();//传递用户信息给主界面
                    user=smartLiveDB.queryUser(usernameEt.getText().toString());
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("user_data",user);
                    startActivity(intent);
                    finish();
                    editor=pref.edit();
                    if(rememberPass.isChecked()) {
                        editor.putString("username", usernameEt.getText().toString());
                        editor.putString("password", passwordEt.getText().toString());
                        editor.putBoolean("remember_password",true);
                    }else {
                        editor.clear();
                    }
                    editor.commit();
                }else {
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
