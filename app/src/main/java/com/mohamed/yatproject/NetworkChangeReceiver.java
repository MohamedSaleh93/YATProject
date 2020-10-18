package com.mohamed.yatproject;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,
                intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.employee_manager_icon)
                .setContentTitle("Network Status")
                .setContentText("Network changed")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);
        createNotificationChannel(notificationManagerCompat);
        notificationManagerCompat.notify(1001, notificationBuilder.build());

    }

    private void createNotificationChannel(NotificationManagerCompat notificationManagerCompat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence networkChangeChannelName = "NetworkChange";
            String networkChangeChannelDescription = "This is channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("2000", networkChangeChannelName,
                    importance);
            channel.setDescription(networkChangeChannelDescription);
            notificationManagerCompat.createNotificationChannel(channel);

        }
    }
}
