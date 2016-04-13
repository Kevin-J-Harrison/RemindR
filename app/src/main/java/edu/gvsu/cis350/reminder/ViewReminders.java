package edu.gvsu.cis350.reminder;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class ViewReminders extends ListActivity {

    ArrayList<ReminderModel> reminderList;

    private ReminderDBHelper dbHelper = new ReminderDBHelper(this);
    private ViewRemindersAdapter listAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.reminder_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button);

        reminderList = new ArrayList<>();

        //get reminders from DB for use
        if(dbHelper.getReminders() != null){
            reminderList = dbHelper.getReminders();
        }

        //generate the list of reminders and set the list adapter
        if(!reminderList.isEmpty()){
            listAdapter = new ViewRemindersAdapter(this, reminderList);
            setListAdapter(listAdapter);
        }

        //Add Reminder Button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get ready to go to the next activity, pass the list through
                Intent myIntent = new Intent(ViewReminders.this, AddEditReminderActivity.class);
                myIntent.putExtra("id", -1);
                ViewReminders.this.startActivity(myIntent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        listAdapter.setReminderList(dbHelper.getReminders());
        listAdapter.notifyDataSetChanged();
    }

    public void startViewIndividualReminderActivity(long id) {
        Intent intent = new Intent(ViewReminders.this, ViewIndividualReminder.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
