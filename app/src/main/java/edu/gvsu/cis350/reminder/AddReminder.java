package edu.gvsu.cis350.reminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

/**
* Written by Andrew Burns
**/


public class AddReminder extends AppCompatActivity {
    public static DoubleLinkedList reminderList;
    EditText titleText = (EditText) findViewById(R.id.ReminderName);
    EditText notesText = (EditText) findViewById(R.id.notesField);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Intent intent = getIntent();
        reminderList = intent.getParcelableExtra("reminders");

        Button submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create new reminder and add it to the list
                Reminder temp = new Reminder();
                temp.setTitle(titleText.getText().toString());
                temp.setNotes(notesText.getText().toString());
                temp.setDate(new Date());
                //unsure how to grab info from time spinners and then properly format into Date
                //for now just setting to current date and time

                reminderList.add(temp);

                Intent myIntent = new Intent(AddReminder.this, ViewReminders.class);
                myIntent.putExtra("reminders", (Parcelable)reminderList); //Optional parameters
                AddReminder.this.startActivity(myIntent);
            }
        });

    }
}
