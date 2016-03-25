package edu.gvsu.cis350.reminder;

import android.net.Uri;

/**
 * Created by Kevin on 2/22/2016.
 */
public class ReminderModel {

    public boolean isEnabled;

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
}
