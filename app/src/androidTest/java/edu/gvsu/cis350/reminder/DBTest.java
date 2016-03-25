package edu.gvsu.cis350.reminder;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class DBTest extends AndroidTestCase {

    private static String COLUMN_NAME_REMINDER_TITLE;
    private static String COLUMN_NAME_REMINDER_NOTES;
    private static int COLUMN_NAME_REMINDER_HOUR;
    private static int COLUMN_NAME_REMINDER_MINUTE;
    private static int COLUMN_NAME_REMINDER_YEAR;
    private static int COLUMN_NAME_REMINDER_MONTH;
    private static int COLUMN_NAME_REMINDER_DAY;
    private static long mDBId;


    //test create Database
    public void testCreateDB(){
        ReminderDBHelper helper = new ReminderDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        assertTrue(db.isOpen());

        db.close();
    }

    //test drop Database
    public void testDropDB(){
        assertTrue(mContext.deleteDatabase(ReminderDBHelper.DATABASE_NAME));
    }

    //test insert data
    public void testInsertData(){
        ReminderDBHelper helper = new ReminderDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        COLUMN_NAME_REMINDER_TITLE = "reminder";
        COLUMN_NAME_REMINDER_NOTES = "reminder test";
        COLUMN_NAME_REMINDER_HOUR = 12;
        COLUMN_NAME_REMINDER_MINUTE = 0;
        COLUMN_NAME_REMINDER_YEAR = 2016;
        COLUMN_NAME_REMINDER_MONTH = 6;
        COLUMN_NAME_REMINDER_DAY = 13;

        ContentValues values = new ContentValues();
        values.put(ReminderDB.Reminder.COLUMN_NAME_REMINDER_TITLE, COLUMN_NAME_REMINDER_TITLE);
        values.put(ReminderDB.Reminder.COLUMN_NAME_REMINDER_NOTES, COLUMN_NAME_REMINDER_NOTES);
        values.put(ReminderDB.Reminder.COLUMN_NAME_REMINDER_HOUR, COLUMN_NAME_REMINDER_HOUR);
        values.put(ReminderDB.Reminder.COLUMN_NAME_REMINDER_MINUTE, COLUMN_NAME_REMINDER_MINUTE);
        values.put(ReminderDB.Reminder.COLUMN_NAME_REMINDER_YEAR, COLUMN_NAME_REMINDER_YEAR);
        values.put(ReminderDB.Reminder.COLUMN_NAME_REMINDER_MONTH, COLUMN_NAME_REMINDER_MONTH);
        values.put(ReminderDB.Reminder.COLUMN_NAME_REMINDER_DAY,COLUMN_NAME_REMINDER_DAY);

        mDBId = db.insert(ReminderDB.Reminder.TABLE_NAME, null, values);
        assertTrue(mDBId != -1);

    }

    // test data is in correct Database
    public void testIsDataCorrectInDB(){
        ReminderDBHelper dbHelper = new ReminderDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(ReminderDB.Reminder.TABLE_NAME, null, null, null, null, null, null);
        assertTrue(cursor.moveToFirst());

        int idColumnIndex = cursor.getColumnIndex(ReminderDB.Reminder._ID);
        int id = cursor.getInt(idColumnIndex);

        int titleColumnIndex = cursor.getColumnIndex(ReminderDB.Reminder.COLUMN_NAME_REMINDER_TITLE);
        String title = cursor.getString(titleColumnIndex);

        int notesColumnIndex = cursor.getColumnIndex(ReminderDB.Reminder.COLUMN_NAME_REMINDER_NOTES);
        String notes = cursor.getString(notesColumnIndex);

        int hourColumnIndex = cursor.getColumnIndex(ReminderDB.Reminder.COLUMN_NAME_REMINDER_HOUR);
        int hour = cursor.getInt(hourColumnIndex);

        int minuteColumnIndex = cursor.getColumnIndex(ReminderDB.Reminder.COLUMN_NAME_REMINDER_MINUTE);
        int minute = cursor.getInt(minuteColumnIndex);

        int yearColumnIndex = cursor.getColumnIndex(ReminderDB.Reminder.COLUMN_NAME_REMINDER_YEAR);
        int year = cursor.getInt(yearColumnIndex);

        int monthColumnIndex = cursor.getColumnIndex(ReminderDB.Reminder.COLUMN_NAME_REMINDER_MONTH);
        int month = cursor.getInt(monthColumnIndex);

        int dayColumnIndex = cursor.getColumnIndex(ReminderDB.Reminder.COLUMN_NAME_REMINDER_DAY);
        int day = cursor.getInt(dayColumnIndex);

        assertEquals(COLUMN_NAME_REMINDER_TITLE, title);
        assertEquals(COLUMN_NAME_REMINDER_NOTES, notes);
        assertEquals(COLUMN_NAME_REMINDER_HOUR, hour);
        assertEquals(COLUMN_NAME_REMINDER_MINUTE, minute);
        assertEquals(COLUMN_NAME_REMINDER_YEAR, year);
        assertEquals(COLUMN_NAME_REMINDER_MONTH, month);
        assertEquals(COLUMN_NAME_REMINDER_DAY, day);

    }

}
