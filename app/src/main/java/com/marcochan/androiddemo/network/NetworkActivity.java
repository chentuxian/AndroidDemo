package com.marcochan.androiddemo.network;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.marcochan.androiddemo.R;
import com.marcochan.androiddemo.network.entity.User;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkActivity extends AppCompatActivity {

    private Handler handler;

    private OkHttpClient client;

    private static final String HOST = "http://192.168.31.136:8080";
    private static final String API_VERSION = "/api/v1/user";

    private static final int INTERNET_REQUEST_CODE = 1000;

    @BindView(R.id.responseTv)
    TextView responseTv;
    @BindView(R.id.addUserBtn)
    Button addUserBtn;
    @BindView(R.id.deleteUserBtn)
    Button deleteUserBtn;
    @BindView(R.id.updateUserBtn)
    Button updateUserBtn;
    @BindView(R.id.getUserBtn)
    Button getUserBtn;
    @BindView(R.id.getUsersBtn)
    Button getUsersBtn;


    private Callback myCallback = new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            handler.post(() -> responseTv.setText(e.toString()));
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String result = Objects.requireNonNull(response.body()).string();
            handler.post(() -> responseTv.setText(result));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);
        handler = new Handler();

        // Here, this is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "应用需要访问网络", Toast.LENGTH_SHORT).show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET_REQUEST_CODE);

                // INTERNET_REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            client = new OkHttpClient();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case INTERNET_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    client = new OkHttpClient();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private MediaType JSON_TYPE = MediaType.get("application/json;charset=utf-8");

    @OnClick(R.id.addUserBtn)
    void addUserBtnClick() {

        if (client == null) return;

        User user = new User(0,"marco chan","Qq2122010");

        // 如果请求体是键值对则使用表单体进行封装
        RequestBody body = RequestBody.create(JSON_TYPE, JSON.toJSONString(user));

        // 构建POST请求
        Request request = new Request.Builder()
                .url(HOST + API_VERSION)
                .post(body)
                .build();

        // 通过请求来构建一个Call
        Call call = client.newCall(request);

        // 然后call可以调用异步的enqueue方法执行,同步的方法很少用
        call.enqueue(myCallback);

    }

    @OnClick(R.id.deleteUserBtn)
    void deleteUserBtnClick() {

        if (client == null) return;

        // 待操作的用户id
        int id = 0;

        // 构建DELETE请求
        Request request = new Request.Builder()
                .url(HOST + API_VERSION + "/" + id)
                .delete()
                .build();

        // 通过请求来构建一个Call
        Call call = client.newCall(request);

        // 然后call可以调用异步的enqueue方法执行,同步的方法很少用
        call.enqueue(myCallback);
    }

    @OnClick(R.id.updateUserBtn)
    void updateUserBtnClick() {
        if (client == null) return;

        // 待更新的User对象
        User user = new User(0,"apple","password");

        RequestBody body = RequestBody.create(JSON_TYPE,JSON.toJSONString(user));

        // 构建DELETE请求
        Request request = new Request.Builder()
                .url(HOST + API_VERSION + "/" + user.getId())
                .put(body)
                .build();

        // 通过请求来构建一个Call
        Call call = client.newCall(request);

        // 然后call可以调用异步的enqueue方法执行,同步的方法很少用
        call.enqueue(myCallback);
    }

    @OnClick(R.id.getUserBtn)
    void getUserBtnClick() {
        if (client == null) return;

        // 待操作的用户id
        int id = 0;

        // 构建DELETE请求
        Request request = new Request.Builder()
                .url(HOST + API_VERSION + "/" + id)
                .get()
                .build();

        // 通过请求来构建一个Call
        Call call = client.newCall(request);

        // 然后call可以调用异步的enqueue方法执行,同步的方法很少用
        call.enqueue(myCallback);
    }

    @OnClick(R.id.getUsersBtn)
    void getUsersBtnClick() {

        if (client == null) return;

        // 构建GET请求
        Request request = new Request.Builder()
                .url(HOST + API_VERSION)
                .get()
                .build();

        // 通过请求来构建一个Call
        Call call = client.newCall(request);

        // 然后call可以调用异步的enqueue方法执行,同步的方法很少用
        call.enqueue(myCallback);
    }


}
