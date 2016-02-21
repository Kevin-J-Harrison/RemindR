package edu.gvsu.cis350.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ViewReminders extends AppCompatActivity {


    ArrayList<Reminder> reminderList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminders);

        reminderList = new ArrayList<>();
        boolean listMade;

        Intent intent = getIntent();
        listMade = intent.getBooleanExtra("listMade", false);

        if (listMade) {
            reminderList = intent.getParcelableExtra("reminders");
        }
        else {
            for (int i = 1; i < 4; i++) {
                Reminder test = new Reminder();
                test.setTitle(("Reminder #" + Integer.toString(i)));
                test.setNotes(("These are notes. " + Integer.toString(i)));
                test.setDate(Calendar.getInstance());
                reminderList.add(test);
            }
            listMade = true;
        }

        Button fab = (Button) findViewById(R.id.button);



        //generate the list of reminders
            populateListView();



        //Add Reminder Button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get ready to go to the next activity, pass the list through
                Intent myIntent = new Intent(ViewReminders.this, AddReminder.class);
                myIntent.putExtra("reminders", reminderList);
                ViewReminders.this.startActivity(myIntent);

            }
        });


    }

    private void populateListView(){
        //Create list of items
        int size = reminderList.size();
        String[] reminderFinalListView = new String[size];

        Reminder temp;
        //build an array of titles
        for (int i = 0; i < size; i++) {
            temp = reminderList.get(i);
            reminderFinalListView[i] = (temp.getTitle() + "\n" +
                    temp.dateToString() + " " + temp.timeToString());
        }

        //Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_cell, reminderFinalListView);

        //Configure ListView
        ListView list = (ListView) findViewById(R.id.reminderListView);
        list.setAdapter(adapter);

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
