package edu.gvsu.cis350.reminder;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Created by alexvansteel on 3/23/16.
 * Clicks on the button to add a reminder.
 * Creates a reminder, verifying that each step is correct along the way.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EspressoTest {


    @Rule
    public ActivityTestRule<ViewReminders> mActivityRule = new ActivityTestRule<>(ViewReminders.class);



    @Test
    public void test01AddReminderButton() {
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

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(scrollTo());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month, day));

    /**
     * Sets a time in the TimePicker
     */
        int hour = 10;
        int minutes = 59;

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(scrollTo());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(hour, minutes));

    /**
     * Sets the address
     */
        onView(withId(R.id.editAddressField)).perform(scrollTo());
        onView(withId(R.id.editAddressField)).perform(typeText("1 Campus Drive"));

    /**
     * Sets the notes
     */
        onView(withId(R.id.editNotesField)).perform(scrollTo());
        onView(withId(R.id.editNotesField)).perform(typeText("notes notes notes"));

    /**
     * Saves Reminder
     */
        onView(withId(R.id.saveEditbutton)).perform(scrollTo());
        onView(withId(R.id.saveEditbutton)).perform(click());
    }


    /**
     * Clicks on the Reminder and verifies the title is the title of the reminder.
     */
    @Test
    public void test02ViewReminder() {
        onData(anything())
                .inAdapterView(withId(android.R.id.list))
                .atPosition(0)
                .perform(click());

    /**
     * Clicks on the edit button
     */
        onView(withId(R.id.editButton)).perform(click());

    /**
     * Edits the fields.
     */
        onView(withId(R.id.displayReminderName)).perform(scrollTo(), clearText(), typeText("New Title"));
        onView(withId(R.id.displayReminderName)).check(matches(withText("New Title")));

        onView(withId(R.id.radioButtonYearly)).perform(click());
        onView(withId(R.id.radioButtonMonthly)).perform(click());
        onView(withId(R.id.radioButtonWeekly)).perform(click());

        onView(withId(R.id.checkBoxSunday)).perform(click());
        onView(withId(R.id.checkBoxMonday)).perform(click());
        onView(withId(R.id.checkBoxTuesday)).perform(click());
        onView(withId(R.id.checkBoxWednesday)).perform(click());
        onView(withId(R.id.checkBoxThursday)).perform(click());
        onView(withId(R.id.checkBoxFriday)).perform(click());
        onView(withId(R.id.checkBoxSaturday)).perform(click());

        onView(withId(R.id.editAddressField)).perform(scrollTo(), clearText(), typeText("DeVos"));
        onView(withId(R.id.editAddressField)).check(matches(withText("DeVos")));

        onView(withId(R.id.editNotesField)).perform(scrollTo(), clearText(), typeText("spam spam eggs spam"));
        onView(withId(R.id.editNotesField)).check(matches(withText("spam spam eggs spam")));

        onView(withId(R.id.saveEditbutton)).perform(scrollTo(), click());
    }

    /**
     * Opens and deletes reminder.
     */
    @Test
    public void test03DeleteReminder() {
        onData(anything())
                .inAdapterView(withId(android.R.id.list))
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.editButton)).perform(click());

        onView(withId(R.id.deleteButton)).perform(scrollTo(), click());

    }
}

