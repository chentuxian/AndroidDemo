package com.marcochan.lib_notification.model;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotificationAction {
    private int icon;
    private String name;
    private PendingIntent pendingIntent;

    public NotificationAction(int icon, String name, PendingIntent pendingIntent) {
        this.icon = icon;
        this.name = name;
        this.pendingIntent = pendingIntent;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public static NotificationAction getActivityAction(Context context, int icon, String name, Class activity) {
        Intent intent = new Intent(context, activity);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        return new NotificationAction(icon, name, pendingIntent);
    }

    public static NotificationAction getServiceAction(Context context, int icon, String name, Class service) {
        Intent intent = new Intent(context, service);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        return new NotificationAction(icon, name, pendingIntent);
    }

    public static NotificationAction getBroadcastAction(Context context, int icon, String name, String broadcastAction) {
        Intent intent = new Intent();
        intent.setAction(broadcastAction);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        return new NotificationAction(icon, name, pendingIntent);
    }


}
