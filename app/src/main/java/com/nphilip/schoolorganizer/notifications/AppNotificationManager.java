package com.nphilip.schoolorganizer.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class AppNotificationManager extends Application {

    public static final String CHANNEL_ID = "Channel 1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    public void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Deadline Reminder", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Deadline Reminder\nDeadline is in under 24 hours.\nThe notification shows the work type.");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
