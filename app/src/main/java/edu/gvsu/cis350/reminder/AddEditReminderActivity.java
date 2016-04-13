package edu.gvsu.cis350.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

/**
 * Written by Andrew Burns
 *
 * Edited by Kevin Harrison 04/10/2014
 **/


public class AddEditReminderActivity extends AppCompatActivity {
    private ReminderDBHelper dbHelper = new ReminderDBHelper(this);
    private ReminderModel reminderInfo;

    private TextView titleText;
    private EditText notesText;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText addressText;

    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_reminder);

        FloatingActionButton submit = (FloatingActionButton) findViewById(R.id.saveEditbutton);
        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.deleteButton);

        titleText = (EditText) findViewById(R.id.displayReminderName);
        notesText = (EditText) findViewById(R.id.editNotesField);
        datePicker = (DatePicker) findViewById(R.id.editDatePicker);
        timePicker = (TimePicker) findViewById(R.id.editTimePicker);
        addressText = (EditText) findViewById(R.id.editAddressField);

        Intent incomingIntent = getIntent();
        final long id = incomingIntent.getExtras().getLong("id", -1);

        if(id == -1) {
            reminderInfo = new ReminderModel();
            delete.setVisibility(View.GONE);
        }

        else {
            reminderInfo = dbHelper.getReminder(id);

            titleText.setText(reminderInfo.title);
            notesText.setText(reminderInfo.notes);
            datePicker.updateDate(reminderInfo.year, reminderInfo.month, reminderInfo.day);
            datePicker.setMinDate(calendar.getTimeInMillis());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(reminderInfo.hour);
            }
            else {
                timePicker.setCurrentHour(reminderInfo.hour);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setMinute(reminderInfo.minute);
            }
            else {
                timePicker.setCurrentMinute(reminderInfo.minute);
            }
            addressText.setText(reminderInfo.address);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int curYear = Calendar.getInstance().get(Calendar.YEAR);
                final int curMonth = Calendar.getInstance().get(Calendar.MONTH);
                final int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                final int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int curMin = Calendar.getInstance().get(Calendar.MINUTE);

                boolean future = false;

                CharSequence text;

                updateReminderInfo();

                //Check date & time to make sure Reminder is set in future. Skip Create if date & time are in the past
                if (reminderInfo.year >= curYear && reminderInfo.month >= curMonth && reminderInfo.day >= curDay
                        && reminderInfo.hour >= curHour && reminderInfo.minute >= curMin) {
                    future = true;
                }
                if(reminderInfo.year >= curYear && reminderInfo.month >= curMonth && reminderInfo.day >= curDay
                        && reminderInfo.hour > curHour) {
                    future = true;
                }

                if(reminderInfo.id == -1 && future == true){
                    dbHelper.createReminder(reminderInfo);
                    text = "Reminder Added";
                }
                else if(reminderInfo.id != -1 && future == true) {
                    dbHelper.updateReminder(reminderInfo);
                    text = "Reminder Saved";
                }
                else if(reminderInfo.id == -1 && future == false){
                    text = "Time set Before Current Time \n " +
                            "      Reminder Not Added!";
                }
                else {
                    text = "Time Set Before Current Time \n " +
                            "     Reminder Not Saved!";
                }

                Context context = getApplicationContext();

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                setAlarm();

                Intent myIntent = new Intent(AddEditReminderActivity.this, ViewReminders.class);
                AddEditReminderActivity.this.startActivity(myIntent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = getApplicationContext();
                CharSequence text = "REMINDER DELETED";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                updateReminderInfo();
                dbHelper.deleteReminder(reminderInfo.id);

                Intent myIntent = new Intent(AddEditReminderActivity.this, ViewReminders.class);
                AddEditReminderActivity.this.startActivity(myIntent);
            }
        });
    }

    // Grab info from spinners & text fields, place into reminderInfo
    private void updateReminderInfo() {
        reminderInfo.title = titleText.getText().toString();
        reminderInfo.notes = notesText.getText().toString();
        reminderInfo.year = datePicker.getYear();
        reminderInfo.month = (datePicker.getMonth()+1);
        reminderInfo.day = datePicker.getDayOfMonth();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            reminderInfo.hour = timePicker.getHour();
        }
        else {
            reminderInfo.hour = timePicker.getCurrentHour();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            reminderInfo.minute = timePicker.getMinute();
        }
        else {
            reminderInfo.minute = timePicker.getCurrentMinute();
        }
        reminderInfo.address = addressText.getText().toString();
    }

    public void setAlarm(){
        Calendar cal = Calendar.getInstance();
        cal.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
                timePicker.getCurrentMinute(), 0);

        Intent alertIntent = new Intent(AddEditReminderActivity.this,AlarmReceiver.class);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                PendingIntent.getBroadcast(AddEditReminderActivity.this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));

    }
}
