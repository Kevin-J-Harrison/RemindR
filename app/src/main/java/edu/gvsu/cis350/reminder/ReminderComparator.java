package edu.gvsu.cis350.reminder;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by Kevin on 04/14/16.
 */
public class ReminderComparator implements Comparator<ReminderModel> {

    @Override
    public int compare(ReminderModel lhs, ReminderModel rhs) {
        Calendar lhsCalendar = Calendar.getInstance();
        Calendar rhsCalendar = Calendar.getInstance();

        lhsCalendar.set(lhs.year, lhs.month, lhs.day, lhs.hour, lhs.minute, 0);
        rhsCalendar.set(rhs.year, rhs.month, rhs.day, rhs.hour, rhs.minute, 0);
        return lhsCalendar.compareTo(rhsCalendar);
    }
}
