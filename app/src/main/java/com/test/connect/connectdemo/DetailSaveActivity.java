package com.test.connect.connectdemo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DetailSaveActivity extends AppCompatActivity {

    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_save);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(title.toLowerCase().equals("create contact")){
            fragment = new CreateContactFragment();
        } else if (title.toLowerCase().equals("create group")){
            fragment = new CreateGroupFragment();
        }

        ft.replace(R.id.frame_detail_save,fragment);
        ft.commit();

    }

}
