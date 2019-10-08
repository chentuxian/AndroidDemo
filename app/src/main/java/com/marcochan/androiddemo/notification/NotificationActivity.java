package com.marcochan.androiddemo.notification;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import com.marcochan.androiddemo.R;
import com.marcochan.lib_notification.NotificationUtil;

import java.util.Objects;

public class NotificationActivity extends AppCompatActivity {

    private static final String TAG = "NotificationDebug";

    // 基础渠道注册信息
    private static final String BASIC_CHANNEL_ID = "BASIC_CHANNEL_ID";
    private static final String BASIC_CHANNEL_NAME = "基础通知";
    private static final String BASIC_CHANNEL_DESCRIPTION = "用于一般应用内的通知";

    // 下载通知的渠道注册信息
    private static final String DOWNLOAD_CHANNEL_ID = "DOWNLOAD_CHANNEL_ID";
    private static final String DOWNLOAD_CHANNEL_NAME = "下载通知";
    private static final String DOWNLOAD_CHANNEL_DESCRIPTION = "用于应用内下载的通知";

    // 记录回复的通知ID
    private int replyId;

    // 监听的广播名称
    private static final String BROADCAST_ACTION_BASIC = "BROADCAST_ACTION_BASIC";
    // 广播接收器,处理监听事情回调
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // 接收到广播了
            Log.d(TAG, Objects.requireNonNull(intent.getAction()));
            Log.d(TAG, "通知的ID:" + intent.getIntExtra("notificationId", -1));

            // 从广播中看看有没有通知的回复
            if (intent.getIntExtra("notificationId", -1) == replyId) {

                CharSequence reply = getMessageText(intent);

                if (reply != null) {
                    String message = reply.toString();
                    replyTv.setText(message);
                    Log.d(TAG, message);
                }

                // 把对应的通知取消掉或者更新
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationActivity.this);
                notificationManager.cancel(replyId);
            } else Log.d(TAG, "当前回复通知的ID:" + replyId);

        }
    };


    // 显示内联回复通知的输入文本
    private TextView replyTv;
    // 内联回复的Key
    private static final String KEY_TEXT_REPLY = "KEY_TEXT_REPLY";


    private Button basicBtn;
    private Button actionBtn;
    private Button replyBtn;
    private Button customBtn;
    private Button downloadBtn;

    // 通知的ID
    private int notificationId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // 注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION_BASIC);
        registerReceiver(receiver, intentFilter);

        // 注册CHANNEL_ID,Android 8.0要求通知必须要有渠道
        NotificationUtil.createNotificationChannel(this, BASIC_CHANNEL_ID, BASIC_CHANNEL_NAME, BASIC_CHANNEL_DESCRIPTION);

        // 注册下载通知的渠道,因为下载的过程中不想要声音,所以单独设置一个渠道
        NotificationUtil.createNotificationChannel(this, DOWNLOAD_CHANNEL_ID, DOWNLOAD_CHANNEL_NAME, DOWNLOAD_CHANNEL_DESCRIPTION, NotificationManagerCompat.IMPORTANCE_LOW);


        // 获取控件
        initView();

        // 初始化控件
        initData();
    }

    /**
     * 获取视图
     */
    private void initView() {
        replyTv = findViewById(R.id.reply_tv);
        basicBtn = findViewById(R.id.basic_btn);
        actionBtn = findViewById(R.id.action_btn);
        replyBtn = findViewById(R.id.reply_btn);
        customBtn = findViewById(R.id.custom_btn);
        downloadBtn = findViewById(R.id.downloadBtn);
    }

    private void initData() {

        // 发送一个基本的通知
        basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 更新notificationId值
                notificationId++;

                // 获取一个通知构建器
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationActivity.this, BASIC_CHANNEL_ID);

                // 设置通知的内容,最基础的是 1.通知图标 2.通知标题 3.通知内容,用户点击通知后默认消失
                builder.setSmallIcon(R.drawable.fire);
                builder.setContentTitle("奥克兰大学");
                builder.setContentText("恭喜你获得奥克兰大学研究生录取资格");
                builder.setAutoCancel(true);

                // 设置点击通知后的操作,把通知ID添加到Intent中,这样经过通知调用的组件就可以获取是哪个ID的通知
                Intent intent = new Intent();
                intent.setAction(BROADCAST_ACTION_BASIC);
                intent.putExtra("notificationId", notificationId);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                // 向Builder添加点击操作,如果不添加默认的操作,那么点击通知后通知不会消失
                builder.setContentIntent(pendingIntent);

                // 获得系统的通知管理器,要求发送通知
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationActivity.this);
                // notificationId 是必须传入的,它是通知的唯一标识
                notificationManager.notify(notificationId, builder.build());


            }
        });

        // 发送一个带按钮操作的通知
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 更新notificationId值
                notificationId++;

                // 获取一个通知构建器
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationActivity.this, BASIC_CHANNEL_ID);

                // 设置通知的内容,最基础的是 1.通知图标 2.通知标题 3.通知内容,用户点击通知后默认消失
                builder.setSmallIcon(R.drawable.fire);
                builder.setContentTitle("奥克兰大学");
                builder.setContentText("恭喜你获得奥克兰大学研究生录取资格");
                builder.setAutoCancel(true);

                // 先创建一个PendingIntent,用于指示按钮操作的行为
                Intent intent = new Intent();
                // 让操作是发送一个广播
                intent.setAction(BROADCAST_ACTION_BASIC);
                intent.putExtra("notificationId", notificationId);
                // 通过PendingIntent的静态方法获取对应的对象
                PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                // 把pendingIntent添加进builder
                builder.addAction(R.drawable.fire, "接受", pendingIntent);

                //为了方便我把点击通知的操作和按钮设为同一个
                builder.setContentIntent(pendingIntent);

                // 获得系统的通知管理器,要求发送通知
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationActivity.this);
                // notificationId 是必须传入的,它是通知的唯一标识
                notificationManager.notify(notificationId, builder.build());

            }
        });

        replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 更新notificationId值
                notificationId++;
                replyId = notificationId;

                // 获取一个通知构建器
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationActivity.this, BASIC_CHANNEL_ID);
                // 设置通知的内容,最基础的是 1.通知图标 2.通知标题 3.通知内容,用户点击通知后默认消失
                builder.setSmallIcon(R.drawable.fire);
                builder.setContentTitle("奥克兰大学");
                builder.setContentText("恭喜你获得奥克兰大学研究生录取资格");
                builder.setAutoCancel(true);

                // 先创建一个PendingIntent,用于指示按钮操作的行为
                Intent intent = new Intent();
                // 让操作是发送一个广播
                intent.setAction(BROADCAST_ACTION_BASIC);
                intent.putExtra("notificationId", notificationId);
                // 通过PendingIntent的静态方法获取对应的对象
                PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //为了方便我把点击通知的操作和按钮设为同一个
                builder.setContentIntent(pendingIntent);


                // 创建一个输入框,给输入框绑定一个KEY,这样后面可以拿到数据
                RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                        .setLabel("提示")
                        .build();

                // 创建一个Action按钮,并向该Action传入输入框和点击按钮后的操作
                NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.fire, "回复", pendingIntent)
                        .setShowsUserInterface(true)
                        .addRemoteInput(remoteInput)
                        .build();
                // 添加进builder
                builder.addAction(action);

                // 获得系统的通知管理器,要求发送通知
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationActivity.this);
                // notificationId 是必须传入的,它是通知的唯一标识
                notificationManager.notify(notificationId, builder.build());

            }
        });


        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadTask().execute(100);
            }
        });

        customBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 更新notificationId值
                notificationId++;

                // 获取一个通知构建器
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationActivity.this, BASIC_CHANNEL_ID);

                // 设置通知的内容,最基础的是 1.通知图标 2.通知标题 3.通知内容,用户点击通知后默认消失
                builder.setSmallIcon(R.drawable.fire);
                builder.setContentTitle("奥克兰大学");
                builder.setContentText("恭喜你获得奥克兰大学研究生录取资格");
                builder.setAutoCancel(false);


                // 设置点击通知后的操作,把通知ID添加到Intent中,这样经过通知调用的组件就可以获取是哪个ID的通知
                Intent intent = new Intent();
                intent.setAction(BROADCAST_ACTION_BASIC);
                intent.putExtra("notificationId", notificationId);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                // 向Builder添加点击操作,如果不添加默认的操作,那么点击通知后通知不会消失
                builder.setContentIntent(pendingIntent);

                // 使用一个XML布局来初始化一个RemoteView
                RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_custom);
                // 设置布局中按钮的事件
                notificationLayout.setOnClickPendingIntent(R.id.preBtn,pendingIntent);
                notificationLayout.setOnClickPendingIntent(R.id.stopBtn,pendingIntent);
                notificationLayout.setOnClickPendingIntent(R.id.nextBtn,pendingIntent);
                // 把这些自定义内容视图添加到builder中
                builder.setCustomContentView(notificationLayout);
                // 使用最基本的通知装饰一下,如果只想显示自己的布局,那么就不要调用
                //builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
                builder.setOngoing(true);


                // 获得系统的通知管理器,要求发送通知
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationActivity.this);
                // notificationId 是必须传入的,它是通知的唯一标识
                notificationManager.notify(notificationId, builder.build());


            }
        });

    }

    /**
     * 获取通知中输入的文本信息
     *
     * @param intent
     * @return
     */
    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(KEY_TEXT_REPLY);
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    /**
     * 异步线程,第一个参数是初始参数,第二个是进度参数类型,第三个是结果参数
     */
    class DownloadTask extends AsyncTask<Integer, Integer, Void> {

        private NotificationCompat.Builder builder;
        private int taskNotificationId;

        /**
         * 第一步
         * 这个方法是在UI线程中执行
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // 更新notificationId值
            notificationId++;
            taskNotificationId = notificationId;

            // 获取一个通知构建器
            builder = new NotificationCompat.Builder(NotificationActivity.this, DOWNLOAD_CHANNEL_ID);

            // 设置通知的内容,最基础的是 1.通知图标 2.通知标题 3.通知内容,用户点击通知后默认消失
            builder.setSmallIcon(R.drawable.fire);
            builder.setContentTitle("软件更新");
            builder.setContentText("正在下载");
            builder.setAutoCancel(true);
            builder.setProgress(100, 0, false);

            // 设置点击通知后的操作,把通知ID添加到Intent中,这样经过通知调用的组件就可以获取是哪个ID的通知
            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION_BASIC);
            intent.putExtra("notificationId", notificationId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // 向Builder添加点击操作,如果不添加默认的操作,那么点击通知后通知不会消失
            builder.setContentIntent(pendingIntent);

            // 获得系统的通知管理器,要求发送通知
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationActivity.this);
            // notificationId 是必须传入的,它是通知的唯一标识
            notificationManager.notify(notificationId, builder.build());
        }

        /**
         * 第二步
         * 该方法是在一个单独的子线程执行,可以执行长时间操作
         * 需要注意:
         * 1.该方法内调用publishProgress可以发布进度
         * 2.在循环中应该多调用isCancel()方法,如果外部线程结束了该Task,那么isCancel()会返回True,这时候应该结束
         *
         * @param integers
         * @return
         */
        @Override
        protected Void doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                try {
                    publishProgress(i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        /**
         * 第三步
         * 该方法在UI线程中执行,可以直接操作UI
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            builder.setProgress(100, values[0], false);
            builder.setPriority(NotificationCompat.PRIORITY_LOW);
            // 获得系统的通知管理器,要求发送通知
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationActivity.this);
            // notificationId 是必须传入的,它是通知的唯一标识
            notificationManager.notify(taskNotificationId, builder.build());
        }

        /**
         * 第四步
         * 这个方法也是在UI线程中执行,当doInBackground结束后就会执行
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // 获得系统的通知管理器,要求发送通知
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(NotificationActivity.this);
            // notificationId 是必须传入的,它是通知的唯一标识
            notificationManager.cancel(taskNotificationId);
        }
    }
}
