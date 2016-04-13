package edu.gvsu.cis350.reminder;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        //create Notification
        PendingIntent notificationIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, ViewReminders.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.splash_img)
                .setTicker("Remind Alert")
                .setContentTitle("Remind")
                .setContentText("Check out your reminders");
        mBuilder.setContentIntent(notificationIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);

        NotificationManager nManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        nManager.notify(1, mBuilder.build());

    }
}
