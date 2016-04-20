package edu.gvsu.cis350.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewReminders extends AppCompatActivity {

    private ReminderDBHelper dbHelper = new ReminderDBHelper(this);
    private ViewRemindersAdapter listAdapter;
    private ListView reminderListView;
    public static int theme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (theme) {
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

        setContentView(R.layout.reminder_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button);

        //generate the list of reminders and set the list adapter
        reminderListView = (ListView)findViewById(android.R.id.list);
        listAdapter = new ViewRemindersAdapter(this, dbHelper.getReminders());
        reminderListView.setAdapter(listAdapter);


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
            Intent myIntent = new Intent(ViewReminders.this, Settings.class);
            ViewReminders.this.startActivity(myIntent);
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

    public void setReminderEnabled(long id, boolean enabled) {
        ReminderModel reminder = dbHelper.getReminder(id);
        reminder.isEnabled = enabled;
        dbHelper.updateReminder(reminder);

        NotificationHelper.setNotifications(this);
    }

    public static void setAppTheme(int newTheme){
        theme = newTheme;
    }

    public static int getAppTheme(){
        return theme;
    }
}
