package edu.gvsu.cis350.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kevin on 4/13/16.
 */
public class NotificationHelper {


    public static void setNotifications(Context context) {
        cancelNotifications(context);

        ReminderDBHelper dbHelper = new ReminderDBHelper(context);

        ArrayList<ReminderModel> reminderList = dbHelper.getReminders();

        if (reminderList != null) {
            for (ReminderModel reminderInfo : reminderList) {

                Calendar curTime = Calendar.getInstance();
                Calendar reminderTime = Calendar.getInstance();
                reminderTime.set(reminderInfo.year, reminderInfo.month, reminderInfo.day, reminderInfo.hour, reminderInfo.minute, 0);

                //Check to see if reminder is enabled
                if (reminderInfo.isEnabled) {
                    //Check date & time to make sure Reminder is set in future. Skip Create if date & time are in the past
                    if (reminderTime.compareTo(curTime) == 1) {

                        PendingIntent pendingIntent = createPendingIntent(context, reminderInfo);

                        Calendar cal = createCalendar(reminderInfo);

                        setNotification(context, cal, pendingIntent);
                    } else {
                        reminderInfo.isEnabled = false;
                        dbHelper.updateReminder(reminderInfo);
                    }
                }
            }
        }
    }

    private static void setNotification(Context context, Calendar calendar, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private static void cancelNotifications(Context context) {
        ReminderDBHelper dbHelper = new ReminderDBHelper(context);

        ArrayList<ReminderModel> reminders =  dbHelper.getReminders();

        if (reminders != null) {
            for (ReminderModel reminder : reminders) {
                PendingIntent pIntent = createPendingIntent(context, reminder);

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pIntent);
            }
        }
    }

    private static Calendar createCalendar(ReminderModel reminderInfo) {
        Calendar cal = Calendar.getInstance();
        cal.set(reminderInfo.year, reminderInfo.month,
                reminderInfo.day, reminderInfo.hour,
                reminderInfo.minute, 0);

        return cal;
    }

    private static PendingIntent createPendingIntent(Context context, ReminderModel reminderInfo) {
        Intent notificationIntent = new Intent(context, AlarmReceiver.class);
        notificationIntent.putExtra("id", reminderInfo.id);
        notificationIntent.putExtra("title", reminderInfo.title);
        notificationIntent.putExtra("notes", reminderInfo.notes);

        return  PendingIntent.getBroadcast(context, (int) reminderInfo.id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
