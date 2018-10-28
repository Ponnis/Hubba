package com.example.nils_martin.hubba.ViewModel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.nils_martin.hubba.R;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

/**
 * @author Camilla Söderlund
 */
public class NotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "hubba.notification";
    private final String NOTIFICATIONSGROUP = "hubbaNotifications";
    int notificationID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivityVM.class);

        TaskStackBuilder stackBuilder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivityVM.class);
            stackBuilder.addNextIntent(notificationIntent);

            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder builder = new Notification.Builder(context);

            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                notification = builder.setContentTitle("Remainder!")
                        .setContentText("Don't forget to " + intent.getStringExtra("HabitNamn"))
                        .setTicker("New Message Alert!")
                        .setSmallIcon(R.drawable.profilepic)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setGroup(NOTIFICATIONSGROUP)
                        .setContentIntent(pendingIntent)
                        .build();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(CHANNEL_ID);
            }

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID,
                        "NotificationDemo",
                        IMPORTANCE_DEFAULT
                );
                notificationManager.createNotificationChannel(channel);
            }

            System.out.println(intent.getStringExtra("HabitNamn"));
            notificationManager.notify(notification.hashCode(), notification);
            notificationID ++;
        }

    }
}


