package edu.gvsu.cis350.reminder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


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
        long id = incomingIntent.getExtras().getLong("id");

        reminderInfo = dbHelper.getReminder(id);

        TextView titleView = (TextView) findViewById(R.id.titleText);
        TextView dateView = (TextView) findViewById(R.id.dateText);
        TextView timeView = (TextView) findViewById(R.id.timeText);
        TextView notesView = (TextView) findViewById(R.id.noteText);
        TextView addressView = (TextView) findViewById(R.id.addressView);

        titleView.setText(reminderInfo.title);
        dateView.setText(String.format("%02d/%02d/%04d", reminderInfo.month + 1, reminderInfo.day, reminderInfo.year));
        timeView.setText(String.format("%02d : %02d", reminderInfo.hour, reminderInfo.minute));
        notesView.setText(reminderInfo.notes);
        addressView.setText(reminderInfo.address);
        map = "http://maps.google.co.in/maps?q=" + reminderInfo.address;


        FloatingActionButton openMap = (FloatingActionButton) findViewById(R.id.viewMap_button);

        if(reminderInfo.address.compareTo("") == 0) {
            openMap.setVisibility(View.GONE);
        }

        //View Map Button
        openMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                ViewIndividualReminder.this.startActivity(intent);
            }
        });


        //Edit Button
        FloatingActionButton editButton = (FloatingActionButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(ViewIndividualReminder.this, AddEditReminderActivity.class);
                editIntent.putExtra("id", reminderInfo.id);
                ViewIndividualReminder.this.startActivity(editIntent);
            }
        });

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
