package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver_SendOn extends BroadcastReceiver {
    String channelId = "Channel ID";  //ID канала
    Integer notifyID =101; //ID уведомления
    CharSequence channelName = "My Channel"; //Имя канала

    public void onReceive(Context context, Intent intent) {
        MyNotification(context, intent);  //Создаем сообщение
        Log.d("MyAPP", "onReceive() called");  //Пишем в лог, добрались в onReceive
        android.os.Debug.waitForDebugger();
    }

    private void MyNotification(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{1000, 2000});
        notificationManager.createNotificationChannel(notificationChannel);

        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title != null ? title : "My Message from Broadcast Receiver")
                .setContentText(text != null ? text : "My test message!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setChannelId(channelId);

        Notification notification = builder.build();

        notificationManager.notify(notifyID, notification);
        Log.d("MyAPP", "Notification set");
    }
}
