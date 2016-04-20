package edu.gvsu.cis350.reminder;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private RadioGroup repeatType;
    private RadioButton repeatOnce;
    private RadioButton repeatYearly;
    private RadioButton repeatMonthly;
    private RadioButton repeatWeekly;
    private CheckBox sunday;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;

    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (ViewReminders.getAppTheme()) {
            case 0:
                this.setTheme(R.style.AppTheme_NoActionBar);
                break;
            case 1:
                this.setTheme(R.style.Flame);
                break;
            case 2:
                this.setTheme(R.style.Forest);
                break;
            case 3:
                this.setTheme(R.style.Sunrise);
                break;
            case 4:
                this.setTheme(R.style.Slate);
                break;
            case 5:
                this.setTheme(R.style.TieDye);
                break;
        }
        setContentView(R.layout.activity_add_edit_reminder);

        FloatingActionButton submit = (FloatingActionButton) findViewById(R.id.saveEditbutton);
        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.deleteButton);

        titleText = (EditText) findViewById(R.id.displayReminderName);
        notesText = (EditText) findViewById(R.id.editNotesField);
        datePicker = (DatePicker) findViewById(R.id.editDatePicker);
        timePicker = (TimePicker) findViewById(R.id.editTimePicker);
        addressText = (EditText) findViewById(R.id.editAddressField);
        repeatType = (RadioGroup) findViewById(R.id.radioGroupRepeat);
        repeatOnce = (RadioButton) findViewById(R.id.radioButtonOnce);
        repeatYearly = (RadioButton) findViewById(R.id.radioButtonYearly);
        repeatMonthly = (RadioButton) findViewById(R.id.radioButtonMonthly);
        repeatWeekly = (RadioButton) findViewById(R.id.radioButtonWeekly);
        sunday = (CheckBox) findViewById(R.id.checkBoxSunday);
        monday = (CheckBox) findViewById(R.id.checkBoxMonday);
        tuesday = (CheckBox) findViewById(R.id.checkBoxTuesday);
        wednesday = (CheckBox) findViewById(R.id.checkBoxWednesday);
        thursday = (CheckBox) findViewById(R.id.checkBoxThursday);
        friday = (CheckBox) findViewById(R.id.checkBoxFriday);
        saturday = (CheckBox) findViewById(R.id.checkBoxSaturday);

        Intent incomingIntent = getIntent();
        final long id = incomingIntent.getExtras().getLong("id", -1);

        if(id == -1) {
            reminderInfo = new ReminderModel();
            delete.setVisibility(View.GONE);
            setCheckBoxesVisible(false);
        }

        else {
            reminderInfo = dbHelper.getReminder(id);
            setFields();
        }

        repeatType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButtonOnce || checkedId == R.id.radioButtonYearly || checkedId == R.id.radioButtonMonthly) {
                    setCheckBoxesVisible(false);
                    setCheckBoxesChecked(false);
                    datePicker.setVisibility(View.VISIBLE);
                }
                else if(checkedId == R.id.radioButtonWeekly) {
                    setCheckBoxesVisible(true);
                    datePicker.setVisibility(View.GONE);
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean future;
                CharSequence text;

                updateReminderInfo();

                future = reminderInfo.futureTime();

                if(repeatWeekly.isChecked()){
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
                cancelAlarm();
                dbHelper.deleteReminder(reminderInfo.id);
                setAlarm();

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
        reminderInfo.month = (datePicker.getMonth());
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

        updateRepeatType();

        if(reminderInfo.weekly == true) {
            updateRepeatDays();
        }
    }

    //update reminder with repeating type from radioButtons
    private void updateRepeatType() {
        reminderInfo.once = repeatOnce.isChecked();
        reminderInfo.yearly = repeatYearly.isChecked();
        reminderInfo.monthly = repeatMonthly.isChecked();
        reminderInfo.weekly = repeatWeekly.isChecked();
    }

    //update reminder with values from repeating day checkboxes
    private void updateRepeatDays() {
        reminderInfo.repeatDays[0] = sunday.isChecked();
        reminderInfo.repeatDays[1] = monday.isChecked();
        reminderInfo.repeatDays[2] = tuesday.isChecked();
        reminderInfo.repeatDays[3] = wednesday.isChecked();
        reminderInfo.repeatDays[4] = thursday.isChecked();
        reminderInfo.repeatDays[5] = friday.isChecked();
        reminderInfo.repeatDays[6] = saturday.isChecked();
    }

    //Call NotificationHelper to set alarms for each enabled reminder.
    private void setAlarm(){
        NotificationHelper.setNotifications(AddEditReminderActivity.this);
    }

    private void cancelAlarm(){
        NotificationHelper.cancelNotifications(AddEditReminderActivity.this);
    }

    private void setRadioButtonFromReminder(){
        repeatOnce.setChecked(reminderInfo.once);
        repeatYearly.setChecked(reminderInfo.yearly);
        repeatMonthly.setChecked(reminderInfo.monthly);
        repeatWeekly.setChecked(reminderInfo.weekly);
    }

    private void setCheckBoxesVisible(Boolean visible) {
        int visibility = View.VISIBLE;
        if(visible == false) {
            visibility = View.GONE;
        }
        sunday.setVisibility(visibility);
        monday.setVisibility(visibility);
        tuesday.setVisibility(visibility);
        wednesday.setVisibility(visibility);
        thursday.setVisibility(visibility);
        friday.setVisibility(visibility);
        saturday.setVisibility(visibility);
    }

    private void setCheckBoxesChecked(boolean checked) {
        sunday.setChecked(checked);
        monday.setChecked(checked);
        tuesday.setChecked(checked);
        wednesday.setChecked(checked);
        thursday.setChecked(checked);
        friday.setChecked(checked);
        saturday.setChecked(checked);
    }

    private void setCheckBoxesForReminder() {
        sunday.setChecked(reminderInfo.repeatDays[0]);
        monday.setChecked(reminderInfo.repeatDays[1]);
        tuesday.setChecked(reminderInfo.repeatDays[2]);
        wednesday.setChecked(reminderInfo.repeatDays[3]);
        thursday.setChecked(reminderInfo.repeatDays[4]);
        friday.setChecked(reminderInfo.repeatDays[5]);
        saturday.setChecked(reminderInfo.repeatDays[6]);
    }

    private void setFields() {
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

        setRadioButtonFromReminder();
        setCheckBoxesForReminder();

        if(reminderInfo.weekly == true) {
            datePicker.setVisibility(View.GONE);
        }
    }
}
