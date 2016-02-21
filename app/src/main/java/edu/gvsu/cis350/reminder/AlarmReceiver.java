package edu.gvsu.cis350.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Kevin on 2/21/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Display reminder message
        Toast.makeText(context, "Remember that thing", Toast.LENGTH_SHORT).show();
    }
}
