package edu.gvsu.cis350.reminder;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_cell, parent, false);
        }

        ReminderModel reminder = (ReminderModel) getItem(position);

        TextView titleText = (TextView) convertView.findViewById(R.id.reminder_item_title);
        titleText.setText(reminder.title);

        TextView dateText = (TextView) convertView.findViewById(R.id.reminder_item_date);
        dateText.setText(String.format("%02d/%02d/%04d", reminder.month + 1, reminder.day, reminder.year));

        TextView timeText = (TextView) convertView.findViewById(R.id.reminder_item_time);
        timeText.setText(String.format("%02d : %02d", reminder.hour, reminder.minute));

        Switch onOffSwitch = (Switch) convertView.findViewById(R.id.reminder_item_toggle);
        onOffSwitch.setTag(reminder.id);
        onOffSwitch.setChecked(reminder.isEnabled);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            onOffSwitch.setTrackTintMode(PorterDuff.Mode.DST_OVER);
        }
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    ((ViewReminders) context).setReminderEnabled((long) buttonView.getTag(), true);
                }
                else {
                    ((ViewReminders) context).setReminderEnabled((long) buttonView.getTag(), false);
                }
            }
        });

        convertView.setTag(reminder.id);
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((ViewReminders) context).startViewIndividualReminderActivity((long) v.getTag());
            }
        });

        return convertView;
    }
}
