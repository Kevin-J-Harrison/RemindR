package edu.gvsu.cis350.reminder;

import android.net.Uri;

import java.util.Calendar;

/**
 * Created by Kevin on 2/22/2016.
 */
public class ReminderModel {

    public boolean isEnabled = true;
    public long id = -1;
    public String title;
    public String notes;
    public int hour;
    public int minute;
    public int year;
    public int month;
    public int day;
    public Uri reminderSound;
    public String address;

    public ReminderModel() {

    }

    public boolean futureTime() {
        Calendar curTime = Calendar.getInstance();
        Calendar reminderTime = Calendar.getInstance();

        reminderTime.set(year, month, day, hour, minute, 0);

        if(reminderTime.compareTo(curTime) == 1) {
            return true;
        }

        return false;
    }
}
