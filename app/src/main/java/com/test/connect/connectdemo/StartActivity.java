package com.test.connect.connectdemo;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    ImageView iv_logo,iv_fb,iv_google,iv_linkedin;
    Button btn_login,btn_register;
    TextView tv_sign,tv_register;
    EditText et_username,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btn_login = (Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,UserHomeActivity.class);
                startActivity(intent);
            }
        });

        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"This is demo version. You can continue by hitting login button.",Snackbar.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
