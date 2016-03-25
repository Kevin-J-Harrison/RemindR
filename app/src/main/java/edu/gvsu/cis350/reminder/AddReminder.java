package edu.gvsu.cis350.reminder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;

import java.util.Calendar;

/**
 * Written by Andrew Burns
 **/


public class AddReminder extends AppCompatActivity {
    private ReminderDBHelper dbHelper = new ReminderDBHelper(this);

    private ReminderModel reminderInfo;

    private EditText titleText;
    private EditText notesText;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText addressText;

    private Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        titleText = (EditText) findViewById(R.id.ReminderName);
        notesText = (EditText) findViewById(R.id.notesField);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.setMinDate(calendar.getTimeInMillis());
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        addressText = (EditText) findViewById(R.id.addressField);


        Button submit = (Button) findViewById(R.id.submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int curYear = Calendar.getInstance().get(Calendar.YEAR);
                final int curMonth = Calendar.getInstance().get(Calendar.MONTH);
                final int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                final int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int curMin = Calendar.getInstance().get(Calendar.MINUTE);

                CharSequence text = "Reminder added!";

                //create new reminder and add it to the list
                reminderInfo = new ReminderModel();
                updateReminderInfo();

                //Check to see if reminderInfo is a new Reminder
                if(reminderInfo.id < 0)
                    //Check date & time to make sure Reminder is set in future. Skip Create if date & time are in the past
                    if(reminderInfo.year >= curYear && reminderInfo.month >= curMonth && reminderInfo.day >= curDay
                            && reminderInfo.hour >= curHour && reminderInfo.minute >= curMin) {
                        dbHelper.createReminder(reminderInfo);
                    }
                    else if(reminderInfo.year >= curYear && reminderInfo.month >= curMonth && reminderInfo.day >= curDay
                            && reminderInfo.hour > curHour) {
                        dbHelper.createReminder(reminderInfo);
                    }
                    else {
                        text = "Time Set Before Current Time \n " +
                                "     Reminder Not Added!";
                    }

                else
                    dbHelper.updateReminder(reminderInfo);

                Context context = getApplicationContext();

                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent myIntent = new Intent(AddReminder.this, ViewReminders.class);
                AddReminder.this.startActivity(myIntent);
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


}
