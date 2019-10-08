package com.marcochan.androiddemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * 该类是Service的子类,主要特点是简化了Service的工作
 * 直接在Intent中封装数据,调用startService,系统就会调用onHandleIntent()处理
 * 并且该方法是一个work thread,不会阻塞UI线程,使用起来简单方便
 */
public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getName();

    public MyIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int time = intent.getIntExtra("time", 0);
        while (--time > 0) {
            try {
                Thread.sleep(1000);
                Log.d(TAG,"工作中:"+time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
