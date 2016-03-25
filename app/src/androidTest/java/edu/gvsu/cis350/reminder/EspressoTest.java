package edu.gvsu.cis350.reminder;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by alexvansteel on 3/23/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {
//
//    private UiDevice mDevice;
//
    private ViewReminders mReminders;

    public EspressoTest() {
        ActivityTestRule<Splashscreen> mActivityRule =
                new ActivityTestRule<>(Splashscreen.class);
    }

    /**
     * Clicks on the button to add a reminder.
     * Creates a reminder, verifying that each step is correct along the way.
     */
    @Test
    public void addReminderTest() {
//        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.ReminderName)).perform(typeText("Reminder Title"));
        onView(withId(R.id.ReminderName)).check(matches(withText("Reminder Title")));

        onView(withId(R.id.notesField)).perform(typeText("reminder notes"));
        onView(withId(R.id.notesField)).check(matches(withText("reminder notes")));

        /**
         * There is an alternate way to do this that involves a lot more step by step instruction.
         * It involves clicking on the spinner object, verifying that the onView string matches what
         * you want to click on. Then selecting it. This SHOULD work just as well. However, the
         * PickerAction used in the example is not working here, but I think it is the name of
         * something from the code they were using as an example. We might be using a different
         * picker name that I don't feel like finding at this point in time.
         */
        int year = 2020;
        int month = 11;
        int day = 15;

        onView(withId(R.id.datePicker)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month + 1, day));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.dateText)).check(matches(withText(year + "/" + month + "/" + day)));


        int hour = 10;
        int minutes = 59;

        onView(withId(R.id.timePicker)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(hour, minutes));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.timeText)).check(matches(withText(hour + ":" + minutes)));
        }

    /**
     * Clicks on the Reminder and verifies the title is the title of the reminder.
     */
    @Test
    public void viewReminderTest() {
        onData(withId(R.id.reminderListView)).perform(click());
        onView(withId(R.id.displayReminderName)).check(matches(withText("Reminder Title")));
    }

    /**
     * Clicks on the Reminder in the list view, then chooses the option to edit the reminder.
     * Values are changed and the reminder is saved. Then it is verified that the changes were updated.
     */
    public void editReminderTest() {
        onData(withId(R.id.reminderListView)).perform(click());
        onView(withId(R.id.editButton)).perform(click());

        onView(withId(R.id.ReminderName)).perform(typeText("New Reminder Title"));
        onView(withId(R.id.notesField)).perform(typeText("Edit reminder notes"));

        int year = 2018;
        int month = 1;
        int day = 25;

        onView(withId(R.id.datePicker)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month + 1, day));
        onView(withId(android.R.id.button1)).perform(click());

        int hour = 5;
        int minutes = 43;

        onView(withId(R.id.timePicker)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(hour, minutes));
        onView(withId(android.R.id.button1)).perform(click());


        onView(withId(R.id.saveEditbutton)).perform(click());

        onView(withId(R.id.ReminderName)).check(matches(withText("New Reminder Title")));
        onView(withId(R.id.notesField)).check(matches(withText("Edit reminder notes")));
        onView(withId(R.id.dateText)).check(matches(withText(year + "/" + month + "/" + day)));
        onView(withId(R.id.timeText)).check(matches(withText(hour + ":" + minutes)));
    }

    /**
     * Clicks on the Reminder, then deletes the reminder. Then checks to make sure the list is null.
     */
    @Test
    public void deleteReminderTest() {
        onData(withId(R.id.reminderListView)).perform(click());
        onView(withId(R.id.deleteButton)).perform(click());
        onData(withId(R.id.reminderListView)).check(null);
    }
}

