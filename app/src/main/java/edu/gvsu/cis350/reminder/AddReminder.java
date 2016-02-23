package edu.gvsu.cis350.reminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Intent intent = getIntent();

        titleText = (EditText) findViewById(R.id.ReminderName);
        notesText = (EditText) findViewById(R.id.notesField);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);


        Button submit = (Button) findViewById(R.id.submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create new reminder and add it to the list
                reminderInfo = new ReminderModel();
                updateReminderInfo();

                if(reminderInfo.id < 0)
                    dbHelper.createReminder(reminderInfo);
                else
                    dbHelper.updateReminder(reminderInfo);

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
        reminderInfo.month = datePicker.getMonth();
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

    }
}
