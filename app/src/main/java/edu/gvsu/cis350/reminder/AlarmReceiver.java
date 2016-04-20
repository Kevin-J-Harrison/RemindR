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
        long id = intent.getExtras().getLong("id");
        String title = intent.getExtras().getString("title", "Remind-r");
        String notes = intent.getExtras().getString("notes", "");

        Intent notifyIntent = new Intent(context, ViewIndividualReminder.class);
        notifyIntent.putExtra("id", id);

        PendingIntent notificationIntent = PendingIntent.getActivity(context, (int) id, notifyIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.splash_img)
                .setTicker("Remind-r Alert")
                .setContentTitle(title)
                .setContentText(notes);
        mBuilder.setContentIntent(notificationIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);

        NotificationManager nManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        nManager.notify(1, mBuilder.build());
    }
}
