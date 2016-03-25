package edu.gvsu.cis350.reminder;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ViewReminders extends AppCompatActivity {

    ArrayList<ReminderModel> reminderList;

    private ReminderDBHelper dbHelper = new ReminderDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_reminders);

        Button fab = (Button) findViewById(R.id.button);

        reminderList = new ArrayList<>();

        if(dbHelper.getReminders() != null){
            reminderList = dbHelper.getReminders();
        }



        //generate the list of reminders
        if(!reminderList.isEmpty())
            populateListView();



        //Add Reminder Button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get ready to go to the next activity, pass the list through
                Intent myIntent = new Intent(ViewReminders.this, AddReminder.class);
                ViewReminders.this.startActivity(myIntent);

            }
        });




    }

    private void populateListView() {
        //Create list of items
        int size = reminderList.size();
        String[] reminderFinalListView = new String[size];

        ReminderModel temp;
        //build an array of titles
        for (int i = 0; i < size; i++) {
            temp = reminderList.get(i);
            reminderFinalListView[i] = (temp.title + "\n" +
                    temp.month + "/" + temp.day + "/" + temp.year + "   " + temp.hour + ":" + temp.minute);
        }

        //Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_cell, reminderFinalListView);

        //Configure ListView
        ListView list = (ListView) findViewById(R.id.reminderListView);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Intent viewThisReminderIntent = new Intent(ViewReminders.this, ViewIndividualReminder.class);
                viewThisReminderIntent.putExtra("position", position);
                startActivity(viewThisReminderIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
