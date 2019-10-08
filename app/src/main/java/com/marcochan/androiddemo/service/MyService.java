package com.marcochan.androiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 服务学习
 * 两种使用方式:
 * 1.在其他组件中调用startService(),系统会依次调用服务对象的onCreate(),onStartCommand()
 * 2.在其他组件中调用bindService(),通过获得的binder对象可以做到:1.调用该binder对象的方法 2.通过binder获得服务对象,然后调用服务的公共方法
 *
 * 两种结束模式:
 * 1.其他组件调用stopService(),如果服务没有绑定其他组件,就会调用onDestroy()销毁自己
 * 2.其他组件调用unbindService(),如果服务的绑定组件为0,且不是使用startService启动,则调用onDestroy()销毁自己
 *
 * 注意事项:
 * 1.如果调用了startService()启动服务,最后也必须调用stopService()或者在服务对象内部调用stopSelf()才能结束服务
 * 2.如果服务是用bindService启动的,则服务绑定组件数为0时自动销毁自己
 *
 */
public class MyService extends Service {

    private static final String TAG = MyService.class.getName();

    // Binder given to clients
    private final IBinder binder = new LocalBinder();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        MyService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

    }

    public void sayHello(){
        Log.d(TAG,"Hello from MyService.class");
    }

}
