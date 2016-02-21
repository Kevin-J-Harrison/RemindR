package edu.gvsu.cis350.reminder;

import java.util.Comparator;

/**

 * Created by Kevin on 2/19/2016.

 */

public class ReminderComparator implements Comparator<Reminder> {

    @Override

    public int compare(Reminder lhs, Reminder rhs) {

        if(lhs.getDate().after(rhs.getDate()))
            return 1;

        if(lhs.getDate().before(rhs.getDate()))
            return -1;

        return 0;

    }

}