package edu.gvsu.cis350.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by Kevin on 04/11/16.
 */

public class ViewRemindersAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ReminderModel> reminderList;

    public ViewRemindersAdapter(Context pContext, ArrayList<ReminderModel> reminders) {
        context = pContext;
        reminderList = reminders;
    }

    public void setReminderList(ArrayList<ReminderModel> reminders) {
        reminderList = reminders;
    }

    @Override
    public int getCount() {
        if (reminderList != null) {
            return reminderList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (reminderList != null) {
            return reminderList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (reminderList != null) {
            return reminderList.get(position).id;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_cell, parent, false);
        }

        ReminderModel reminder = (ReminderModel) getItem(position);

        TextView titleText = (TextView) convertView.findViewById(R.id.reminder_item_title);
        titleText.setText(reminder.title);

        TextView dateText = (TextView) convertView.findViewById(R.id.reminder_item_date);
        dateText.setText(String.format("%02d/%02d/%04d", reminder.month, reminder.day, reminder.year));

        TextView timeText = (TextView) convertView.findViewById(R.id.reminder_item_time);
        timeText.setText(String.format("%02d : %02d", reminder.hour, reminder.minute));

        ToggleButton onOffButton = (ToggleButton) convertView.findViewById(R.id.reminder_item_toggle);
        onOffButton.setTag(Long.valueOf(reminder.id));

        convertView.setTag(Long.valueOf(reminder.id));
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((ViewReminders) context).startViewIndividualReminderActivity((Long) v.getTag());
            }
        });

        return convertView;
    }
}
