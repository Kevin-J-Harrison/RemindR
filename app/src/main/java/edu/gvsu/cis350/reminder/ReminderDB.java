package edu.gvsu.cis350.reminder;

import android.provider.BaseColumns;

/**
 * Created by Kevin on 2/23/16.
 */


public class ReminderDB {

    public ReminderDB () {

    }
    public static class Reminder implements BaseColumns {
        public static final String TABLE_NAME = "reminders";
        public static final String COLUMN_NAME_REMINDER_TITLE = "title";
        public static final String COLUMN_NAME_REMINDER_NOTES = "notes";
        public static final String COLUMN_NAME_REMINDER_HOUR = "hour";
        public static final String COLUMN_NAME_REMINDER_MINUTE = "minute";
        public static final String COLUMN_NAME_REMINDER_YEAR = "year";
        public static final String COLUMN_NAME_REMINDER_MONTH = "month";
        public static final String COLUMN_NAME_REMINDER_DAY = "day";
        public static final String COLUMN_NAME_REMINDER_ADDRESS = "address";
        //public static final String COLUMN_NAME_REMINDER_SOUND = "sound";
    }
}