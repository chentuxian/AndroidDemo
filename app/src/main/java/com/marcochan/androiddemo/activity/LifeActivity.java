package com.marcochan.androiddemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.marcochan.androiddemo.R;
import com.marcochan.androiddemo.notification.NotificationActivity;

/**
 * Activity的生命周期
 */
public class LifeActivity extends AppCompatActivity {

    private static final String TAG = LifeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        // Activity 正在被创建
        Log.d(TAG, "onCreate");

        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Activity 即将变成可见状态
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Activity 已经变成可见状态
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 其他的Activity获取了焦点
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 该Activity变成完全不可见
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Activity 即将被销毁
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "保存Activity数据:onSaveInstanceState");
    }
}
