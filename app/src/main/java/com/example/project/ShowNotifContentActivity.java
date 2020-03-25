package com.example.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ShowNotifContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notif_content);
        getSupportActionBar().hide();
    }
}
