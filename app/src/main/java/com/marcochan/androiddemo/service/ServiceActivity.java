package com.marcochan.androiddemo.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.marcochan.androiddemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用于学习Service的使用
 */
public class ServiceActivity extends AppCompatActivity {

    @BindView(R.id.startBtn)
    Button startBtn;

    @BindView(R.id.stopBtn)
    Button stopBtn;

    @BindView(R.id.bindBtn)
    Button bindBtn;

    @BindView(R.id.unbindBtn)
    Button unbindBtn;

    @BindView(R.id.sayHelloBtn)
    Button sayHelloBtn;

    @BindView(R.id.intentServiceBtn)
    Button intentServiceBtn;

    // 服务的对象
    private MyService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.startBtn)
    void startBtnClick() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    @OnClick(R.id.stopBtn)
    void stopBtnClick() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
        // 即使停止服务或者解绑服务,服务依旧存在于缓存,所以需要手动删除引用
        mService = null;
    }

    @OnClick(R.id.bindBtn)
    void bindBtnClick() {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.unbindBtn)
    void unbindBtnClick() {
        unbindService(connection);
        // 即使停止服务或者解绑服务,服务依旧存在于缓存,所以需要手动删除引用
        mService = null;

    }

    @OnClick(R.id.sayHelloBtn)
    void sayHelloBtnClick() {
        if (mService != null) mService.sayHello();
    }

    @OnClick(R.id.intentServiceBtn)
    void intentServiceBtnClick() {
        Intent intent = new Intent(this,MyIntentService.class);
        intent.putExtra("time",5);
        startService(intent);
    }




    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyService.LocalBinder binder = (MyService.LocalBinder) service;
            mService = binder.getService();
        }

        /**
         * 该方法并不会在unbindService调用后调用
         * 官方解释:这是在服务丢失或者崩溃的极端情况才会调用
         * @param arg0
         */
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
        }
    };


}
