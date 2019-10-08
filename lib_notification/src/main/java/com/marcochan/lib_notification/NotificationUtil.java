package com.marcochan.lib_notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.marcochan.lib_notification.model.NotificationAction;

public class NotificationUtil {

    private static int notificationId = 0;

    /**
     * 创建通知的渠道
     *
     * @param context
     * @param channelId
     * @param name
     * @param description
     */
    public static void createNotificationChannel(Context context, String channelId, String name, String description) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //渠道的重要程度
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public static void createNotificationChannel(Context context, String channelId, String name, String description, int importance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * 创建并显示一个基本的通知
     *
     * @param context
     * @param channel
     * @param icon
     * @param title
     * @param content
     * @param activity
     * @return
     */
    public static int notifyBasic(Context context, String channel, int icon, String title, String content, Class activity) {

        return notify(context, getBasicBuilder(context, channel, icon, title, content, activity));

    }

    /**
     * 创建一个包含按钮的通知
     *
     * @param context
     * @param channel
     * @param icon
     * @param title
     * @param content
     * @param activity
     * @param actions
     * @return
     */
    public static int notifyWithAction(Context context, String channel, int icon, String title, String content, Class activity, NotificationAction... actions) {

        //获取基本的构建器
        NotificationCompat.Builder builder = getBasicBuilder(context, channel, icon, title, content, activity);

        if (actions != null && actions.length > 0) {
            for (NotificationAction notificationAction : actions) {
                builder.addAction(notificationAction.getIcon(), notificationAction.getName(), notificationAction.getPendingIntent());
            }
        }

        return notify(context, builder);
    }


    /**
     * 获取一个通知的构建器对象(预先设置好通用参数,便于使用)
     *
     * @param context
     * @param channel
     * @param icon
     * @param title
     * @param content
     * @param activity
     * @return
     */
    private static NotificationCompat.Builder getBasicBuilder(Context context, String channel, int icon, String title, String content, Class activity) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (activity != null) {
            Intent intent = new Intent(context, activity);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentIntent(pendingIntent);
        }

        return builder;
    }

    /**
     * 显示通知
     *
     * @param context
     * @param builder
     * @return
     */
    private static int notify(Context context, NotificationCompat.Builder builder) {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define

        notificationManager.notify(++notificationId, builder.build());

        return notificationId;
    }

}
