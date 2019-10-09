package com.marcochan.androiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.marcochan.androiddemo.google.one.debugger.DebuggerActivity;
import com.marcochan.androiddemo.google.one.intent.ImplicitIntentActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = new Intent();

        intent.setClass(this, DebuggerActivity.class);

        startActivity(intent);

    }
}
