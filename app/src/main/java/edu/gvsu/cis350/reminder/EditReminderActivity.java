package edu.gvsu.cis350.reminder;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Written by Andrew Burns
 **/


public class EditReminderActivity extends AppCompatActivity {
    private ReminderDBHelper dbHelper = new ReminderDBHelper(this);

    private ReminderModel reminderInfo;

    private TextView titleText;
    private EditText notesText;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private ArrayList<ReminderModel> reminderList;
    private EditText addressText;

    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);


        titleText = (TextView) findViewById(R.id.displayReminderName);
        notesText = (EditText) findViewById(R.id.editNotesField);
        datePicker = (DatePicker) findViewById(R.id.editDatePicker);
        timePicker = (TimePicker) findViewById(R.id.editTimePicker);
        addressText = (EditText) findViewById(R.id.editAddressField);

        Intent incomingIntent = getIntent();
        final int i = incomingIntent.getIntExtra("position", 0);

        //grab info from master reminder list for relevant reminder
        reminderList = new ArrayList<>();

        if(dbHelper.getReminders() != null){
            reminderList = dbHelper.getReminders();
        }

        reminderInfo = reminderList.get(i);

        titleText.setText(reminderInfo.title);
        notesText.setText(reminderInfo.notes);
        datePicker.updateDate(reminderInfo.year, reminderInfo.month, reminderInfo.day);
        datePicker.setMinDate(calendar.getTimeInMillis());
        timePicker.setCurrentHour(reminderInfo.hour);
        timePicker.setCurrentMinute(reminderInfo.minute);
        addressText.setText(reminderInfo.address);


        Button submit = (Button) findViewById(R.id.saveEditbutton);
        Button delete = (Button) findViewById(R.id.deleteButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reminderInfo = reminderList.get(i);

                final int curYear = Calendar.getInstance().get(Calendar.YEAR);
                final int curMonth = Calendar.getInstance().get(Calendar.MONTH);
                final int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                final int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int curMin = Calendar.getInstance().get(Calendar.MINUTE);

                CharSequence text = "Reminder saved!";

                updateReminderInfo();

                //Check that reminder is not new
                if(reminderInfo.id > 0) {
                    //Check date & time to make sure Reminder is set in future. Skip Create if date & time are in the past
                    if (reminderInfo.year >= curYear && reminderInfo.month >= curMonth && reminderInfo.day >= curDay
                            && reminderInfo.hour >= curHour && reminderInfo.minute >= curMin) {
                        dbHelper.updateReminder(reminderInfo);
                    }
                    else if(reminderInfo.year >= curYear && reminderInfo.month >= curMonth && reminderInfo.day >= curDay
                            && reminderInfo.hour > curHour) {
                        dbHelper.updateReminder(reminderInfo);
                    }
                    else {
                        text = "Time Set Before Current Time \n " +
                                "     Reminder Not Saved!";
                    }
                }


                Context context = getApplicationContext();

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


                Intent myIntent = new Intent(EditReminderActivity.this, ViewReminders.class);
                EditReminderActivity.this.startActivity(myIntent);
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



                Intent myIntent = new Intent(EditReminderActivity.this, ViewReminders.class);
                EditReminderActivity.this.startActivity(myIntent);
            }
        });


    }

    // Grab info from spinners & text fields, place into reminderInfo
    private void updateReminderInfo() {
        // reminderInfo.title = titleText.getText().toString();
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
