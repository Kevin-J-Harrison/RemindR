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

import java.util.ArrayList;
import java.util.Calendar;

/**
* Written by Andrew Burns
**/


public class AddReminder extends AppCompatActivity {
    ArrayList<Reminder> reminderList;
    boolean listMade = true;
    EditText titleText;
    EditText notesText;
    Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        Intent intent = getIntent();
        reminderList = intent.getParcelableArrayListExtra("reminders");
        titleText = (EditText) findViewById(R.id.ReminderName);
        notesText = (EditText) findViewById(R.id.notesField);


        Button submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create new reminder and add it to the list
                Reminder temp = new Reminder();
                temp.setTitle(titleText.getText().toString());
                temp.setNotes(notesText.getText().toString());
                temp.setDate(date);
                //unsure how to grab info from time spinners and then properly format into Date
                //for now just setting to current date and time

                reminderList.add(temp);

                Intent myIntent = new Intent(AddReminder.this, ViewReminders.class);
                myIntent.putExtra("reminders", reminderList); //Optional parameters
                myIntent.putExtra("listMade", listMade);
                AddReminder.this.startActivity(myIntent);
            }
        });

    }
}
