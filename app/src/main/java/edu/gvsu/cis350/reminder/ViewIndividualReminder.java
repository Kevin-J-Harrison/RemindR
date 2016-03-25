package edu.gvsu.cis350.reminder;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.location.Location;
import android.location.LocationListener;

import java.util.ArrayList;
import java.util.Locale;


public class ViewIndividualReminder extends AppCompatActivity {

    private ReminderDBHelper dbHelper = new ReminderDBHelper(this);

    private ReminderModel reminderInfo;

    private String map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_individual_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



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
        TextView addressView = (TextView) findViewById(R.id.addressView);

        titleView.setText(temp.title);
        dateView.setText(temp.month + "/" + temp.day + "/" + temp.year);
        timeView.setText(temp.hour + ":" + temp.minute);
        notesView.setText(temp.notes);
        addressView.setText(temp.address);
        map = "http://maps.google.co.in/maps?q=" + temp.address;





        Button openMap = (Button) findViewById(R.id.viewMap_button);

        //View Map Button
        openMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                ViewIndividualReminder.this.startActivity(intent);
            }
        });


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
