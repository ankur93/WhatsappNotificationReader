package com.example.ankur.notificationreader.service;

import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;;

public class AnkurNotificationListenerService extends NotificationListenerService {

    private static final String NOTIFICATION_DATA = "Notification Data";
    private static final String NOTIFICATION_STATUS = "Notification Status";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {

        final String pack = sbn.getPackageName();
        if(pack.equals("com.whatsapp")) {
            final Bundle extras = sbn.getNotification().extras;
            final String title = String.valueOf(extras.getString("android.title"));
            final String text = String.valueOf(extras.getCharSequence("android.text"));

            Log.i(NOTIFICATION_DATA, "Package: " + pack);
            Log.i(NOTIFICATION_DATA, "Title: " + title);
            Log.i(NOTIFICATION_DATA, "Text: " + text);
            Log.i(NOTIFICATION_DATA, "==============");

            final Intent msgrcv = new Intent("Msg");
            msgrcv.putExtra("package", pack);
            msgrcv.putExtra("title", title);
            msgrcv.putExtra("text", text);

            LocalBroadcastManager.getInstance(this).sendBroadcast(msgrcv);
        }
    }
    public void onNotificationRemoved(final StatusBarNotification sbn) {
        Log.i(NOTIFICATION_STATUS,"Notification Removed");

    }

}
