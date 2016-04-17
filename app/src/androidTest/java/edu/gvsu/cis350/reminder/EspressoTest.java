package edu.gvsu.cis350.reminder;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by alexvansteel on 3/23/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {


    @Rule
    public ActivityTestRule<ViewReminders> mActivityRule =
            new ActivityTestRule<>(ViewReminders.class);


    /**
     * Clicks on the button to add a reminder.
     * Creates a reminder, verifying that each step is correct along the way.
     */
    @Test
    public void addReminder() {

        /**
         * Clicks the button to create a new reminder.
         */
        onView(withId(R.id.button)).perform(click());

        /**
         * Types into the Title field.
         */
        onView(withId(R.id.displayReminderName)).perform(typeText("It's A Title"));
        onView(withId(R.id.displayReminderName)).check(matches(withText("It's A Title")));

        /**
         * Sets a date in the DatePicker
         */
        int year = 2016;
        int month = 4;
        int day = 20;

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month + 1, day));

        /**
         * Sets a time in the TimePicker
         */
        int hour = 10;
        int minutes = 59;

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(scrollTo());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(hour, minutes));

        /**
         * Enters an address
         */
        onView(withId(R.id.editAddressField)).perform(scrollTo());
        onView(withId(R.id.editAddressField)).perform(typeText("1 Campus Drive"));
        onView(withId(R.id.editAddressField)).check(matches(withText("1 Campus Drive")));

        /**
         * Enter into Notes
         */
        onView(withId(R.id.editNotesField)).perform(scrollTo());
        onView(withId(R.id.editNotesField)).perform(typeText("Notes notes notes"));
        onView(withId(R.id.editNotesField)).check(matches(withText("Notes notes notes")));

        /**
         * Saves reminder
         */
        onView(withId(R.id.saveEditbutton)).perform(scrollTo());
        onView(withId(R.id.saveEditbutton)).perform(click());

        /**
         * Clicks on the Reminder and verifies the title is the title of the reminder.
         */
        onData(isClickable())
                .inAdapterView(withText("rem"))
                .perform(click());
    }
//
//    /**
//     * Clicks on the Reminder in the list view, then chooses the option to edit the reminder.
//     * Values are changed and the reminder is saved. Then it is verified that the changes were updated.
//     */
//    //@Test
//    public void editReminderTest() {
//
//        onView(withId(R.id.editButton)).perform(click());
//
//        onView(withId(R.id.ReminderName)).perform(typeText("New Reminder Title"));
//        onView(withId(R.id.notesField)).perform(typeText("Edit reminder notes"));
//
//        /**
//         * Specifically for datePicker
//         */
//        int year = 2018;
//        int month = 1;
//        int day = 25;
//
//        onView(withId(R.id.datePicker)).perform(click());
//        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
//                .perform(PickerActions.setDate(year, month + 1, day));
//        onView(withId(android.R.id.button1)).perform(click());
//
//        /**
//         * Specificaly for timePicker
//         */
//        int hour = 5;
//        int minutes = 43;
//
//        onView(withId(R.id.timePicker)).perform(click());
//        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
//                .perform(PickerActions.setTime(hour, minutes));
//        onView(withId(android.R.id.button1)).perform(click());
//
//
//        onView(withId(R.id.saveEditbutton)).perform(click());
//
//        onView(withId(R.id.ReminderName)).check(matches(withText("New Reminder Title")));
//        onView(withId(R.id.notesField)).check(matches(withText("Edit reminder notes")));
//        onView(withId(R.id.dateText)).check(matches(withText(year + "/" + month + "/" + day)));
//        onView(withId(R.id.timeText)).check(matches(withText(hour + ":" + minutes)));
//    }
//
//    /**
//     * Clicks on the Reminder, then deletes the reminder. Then checks to make sure the list is null.
//     */
//    //@Test
//    public void deleteReminderTest() {
//
//        onData(withId(reminderListView)).perform(click());
//
//        onView(withId(R.id.editButton)).perform(scrollTo());
//        onView(withId(R.id.editButton)).perform(click());
//
//        onView(withId(R.id.deleteButton)).perform((scrollTo()));
//        onView(withId(R.id.deleteButton)).perform(click());
//
//        onData(withId(reminderListView)).check(null);
//    }
//
//
}

