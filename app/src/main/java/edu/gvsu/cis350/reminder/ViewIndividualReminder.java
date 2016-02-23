package edu.gvsu.cis350.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class ViewIndividualReminder extends AppCompatActivity {

    private ReminderDBHelper dbHelper = new ReminderDBHelper(this);

    private ReminderModel reminderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_individual_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        Intent incomingIntent = getIntent();
        final int i = incomingIntent.getIntExtra("position", 0);



        //grab info from master reminder list for relevant reminder
        ArrayList<ReminderModel> reminderList = new ArrayList<>();

        if(dbHelper.getReminders() != null){
            reminderList = dbHelper.getReminders();
        }
        ReminderModel temp = reminderList.get(i);

        TextView titleView = (TextView) findViewById(R.id.titleText);
        TextView dateView = (TextView) findViewById(R.id.dateText);
        TextView timeView = (TextView) findViewById(R.id.timeText);
        TextView notesView = (TextView) findViewById(R.id.noteText);

        titleView.setText(temp.title);
        dateView.setText(temp.month + "/" + temp.day + "/" + temp.year);
        timeView.setText(temp.hour + ":" + temp.minute);
        notesView.setText(temp.notes);


        //Edit Button
        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(ViewIndividualReminder.this, EditReminderActivity.class);
                editIntent.putExtra("position", i);
                ViewIndividualReminder.this.startActivity(editIntent);
            }
        });

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
