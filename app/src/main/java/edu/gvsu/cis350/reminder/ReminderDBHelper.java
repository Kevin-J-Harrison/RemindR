package edu.gvsu.cis350.reminder;

/**
 * Created by Kevin on 2/23/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import edu.gvsu.cis350.reminder.ReminderDB.Reminder;

public class ReminderDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "reminderDB.db";

    private static final String SQL_CREATE_REMINDER = "CREATE TABLE " + Reminder.TABLE_NAME + " (" +
            Reminder._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Reminder.COLUMN_NAME_REMINDER_TITLE + " TEXT," +
            Reminder.COLUMN_NAME_REMINDER_NOTES + " TEXT," +
            Reminder.COLUMN_NAME_REMINDER_HOUR + " INTEGER," +
            Reminder.COLUMN_NAME_REMINDER_MINUTE + " INTEGER," +
            Reminder.COLUMN_NAME_REMINDER_YEAR + " INTEGER," +
            Reminder.COLUMN_NAME_REMINDER_MONTH + " INTEGER," +
            Reminder.COLUMN_NAME_REMINDER_DAY + " INTEGER," +
            Reminder.COLUMN_NAME_REMINDER_ADDRESS + " STRING" +
            " )";

    private static final String SQL_DELETE_REMINDER =
            "DROP TABLE IF EXISTS " + Reminder.TABLE_NAME;

    public ReminderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_REMINDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_REMINDER);
        onCreate(db);
    }

    private ReminderModel populateModel(Cursor c) {
        ReminderModel model = new ReminderModel();
        model.id = c.getLong(c.getColumnIndex(Reminder._ID));
        model.title = c.getString(c.getColumnIndex(Reminder.COLUMN_NAME_REMINDER_TITLE));
        model.notes = c.getString(c.getColumnIndex(Reminder.COLUMN_NAME_REMINDER_NOTES));
        model.hour = c.getInt(c.getColumnIndex(Reminder.COLUMN_NAME_REMINDER_HOUR));
        model.minute = c.getInt(c.getColumnIndex(Reminder.COLUMN_NAME_REMINDER_MINUTE));
        model.year = c.getInt(c.getColumnIndex(Reminder.COLUMN_NAME_REMINDER_YEAR));
        model.month = c.getInt(c.getColumnIndex(Reminder.COLUMN_NAME_REMINDER_MONTH));
        model.day = c.getInt(c.getColumnIndex(Reminder.COLUMN_NAME_REMINDER_DAY));
        model.address = c.getString(c.getColumnIndex(Reminder.COLUMN_NAME_REMINDER_ADDRESS));


        //model.reminderSound = c.getString(c.getColumnIndex(Reminder.COLUMN_NAME_REMINDER_SOUND)) != "" ? Uri.parse(c.getString(c.getColumnIndex(Reminder.COLUMN_NAME_REMINDER_SOUND))) : null;


        return model;
    }

    private ContentValues populateContent(ReminderModel model) {
        ContentValues values = new ContentValues();
        values.put(Reminder.COLUMN_NAME_REMINDER_TITLE, model.title);
        values.put(Reminder.COLUMN_NAME_REMINDER_NOTES, model.notes);
        values.put(Reminder.COLUMN_NAME_REMINDER_HOUR, model.hour);
        values.put(Reminder.COLUMN_NAME_REMINDER_MINUTE, model.minute);
        values.put(Reminder.COLUMN_NAME_REMINDER_YEAR, model.year);
        values.put(Reminder.COLUMN_NAME_REMINDER_MONTH, model.month);
        values.put(Reminder.COLUMN_NAME_REMINDER_DAY, model.day);
        values.put(Reminder.COLUMN_NAME_REMINDER_ADDRESS, model.address);
        //values.put(Reminder.COLUMN_NAME_REMINDER_Sound, model.reminderTone != null ? model.reminderSound.toString() : "");

        return values;
    }

    public long createReminder(ReminderModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().insert(Reminder.TABLE_NAME, null, values);
    }

    public long updateReminder(ReminderModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().update(Reminder.TABLE_NAME, values, Reminder._ID + " = ?", new String[]{String.valueOf(model.id)});
    }

    public ReminderModel getReminder(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Reminder.TABLE_NAME + " WHERE " + Reminder._ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return populateModel(c);
        }

        c.close();

        return null;
    }

    public ArrayList<ReminderModel> getReminders() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Reminder.TABLE_NAME;

        Cursor c = db.rawQuery(select, null);

        ArrayList<ReminderModel> reminderList = new ArrayList<ReminderModel>();

        while (c.moveToNext()) {
            reminderList.add(populateModel(c));
        }

        if (!reminderList.isEmpty()) {
            return reminderList;
        }

        c.close();

        return null;
    }

    public int deleteReminder(long id) {
        return getWritableDatabase().delete(Reminder.TABLE_NAME, Reminder._ID + " = ?", new String[] { String.valueOf(id) });
    }

}