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

                //Check if reminder is enabled
                if (reminderInfo.isEnabled) {
                    //check for one time, yearly, or monthly reminder type
                    if (reminderInfo.once || reminderInfo.yearly || reminderInfo.monthly) {

                        //Check date & time to make sure Reminder is set in future.
                        if (reminderInfo.futureTime()) {

                            PendingIntent pendingIntent = createPendingIntent(context, reminderInfo);

                            Calendar cal = createCalendar(reminderInfo);

                            setNotification(context, cal, pendingIntent);

                            //reminder is in the past, update time
                        } else {
                            //Check if reminder is one time & disable if true
                            if (reminderInfo.once) {
                                reminderInfo.isEnabled = false;
                                dbHelper.updateReminder(reminderInfo);
                            }
                            //else reminder is monthly or yearly
                            else {
                                //if reminder is yearly increment the year & set alarm
                                if (reminderInfo.yearly) {
                                    PendingIntent pendingIntent = createPendingIntent(context, reminderInfo);
                                    reminderInfo.year++;
                                    dbHelper.updateReminder(reminderInfo);
                                    Calendar cal = createCalendar(reminderInfo);

                                    setNotification(context, cal, pendingIntent);
                                }
                                //else reminder is monthly
                                else {
                                    //month is December, cycle back to January & increment year
                                    if (reminderInfo.month == 11) {
                                        reminderInfo.month = 0;
                                        reminderInfo.year++;
                                        dbHelper.updateReminder(reminderInfo);
                                    }
                                    //else increment month
                                    else {
                                        reminderInfo.month++;
                                        dbHelper.updateReminder(reminderInfo);
                                    }
                                    PendingIntent pendingIntent = createPendingIntent(context, reminderInfo);
                                    Calendar cal = createCalendar(reminderInfo);

                                    setNotification(context, cal, pendingIntent);
                                }
                            }
                        }

                    }

                    //else reminder is weekly
                    else {
                        PendingIntent pIntent = createPendingIntent(context, reminderInfo);
                        Calendar reminderTime = Calendar.getInstance();

                        reminderTime.set(Calendar.HOUR_OF_DAY, reminderInfo.hour);
                        reminderTime.set(Calendar.MINUTE, reminderInfo.minute);
                        reminderTime.set(Calendar.SECOND, 0);

                        //Find next time to set
                        final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                        final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                        final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                        boolean reminderSet = false;

                        //Check if next time is in the future
                        for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                            if (reminderInfo.getRepeatingDay(dayOfWeek - 1) && dayOfWeek >= nowDay &&
                                    !(dayOfWeek == nowDay && reminderInfo.hour < nowHour) &&
                                    !(dayOfWeek == nowDay && reminderInfo.hour == nowHour && reminderInfo.minute <= nowMinute)) {
                                reminderTime.set(Calendar.DAY_OF_WEEK, dayOfWeek);

                                setNotification(context, reminderTime, pIntent);
                                reminderSet = true;
                            }
                        }

                        //Else check if next time is earlier in the week
                        if (!reminderSet) {
                            for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                                if (reminderInfo.getRepeatingDay(dayOfWeek - 1) && dayOfWeek <= nowDay && reminderInfo.weekly) {
                                    reminderTime.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                                    reminderTime.add(Calendar.WEEK_OF_YEAR, 1);

                                    setNotification(context, reminderTime, pIntent);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private static void setNotification(Context context, Calendar calendar, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static void cancelNotifications(Context context) {
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
