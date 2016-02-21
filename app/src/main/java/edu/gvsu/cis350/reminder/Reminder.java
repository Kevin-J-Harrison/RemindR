package edu.gvsu.cis350.reminder;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**

 * Created by Kevin Harrison on 2/19/2016.

 */

public class Reminder implements Parcelable {

    private String title;

    private Calendar date;

    private String notes;

    public Reminder (){

        title = " ";

        notes = " ";

    }

    public void setTitle(String titleP){

        title = titleP;

    }

    public void setDate(Calendar reminderDate){

        date = reminderDate;

    }

    public void setNotes(String notesP){

        notes = notesP;

    }

    public String getTitle(){

        return title;

    }

    public Calendar getDate(){

        return date;

    }

    public String getNotes(){

        return notes;

    }

    //formats the date into a string
    public String dateToString(){

        String out = (date.get(Calendar.MONTH) + "-" + date.get(Calendar.DAY_OF_MONTH) + "-" + date.get(Calendar.YEAR));
        return out;
    }


    //formats the time into a string
    public String timeToString(){
        String out = (date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE));
        if(date.get(Calendar.AM_PM) == 0)
            out = (out + " AM");
        else
            out = (out + " PM");

        return out;
    }

    /* everything below here is for implementing Parcelable */
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(notes);
        out.writeInt(date.get(Calendar.MINUTE));
        out.writeInt(date.get(Calendar.HOUR));
        out.writeInt(date.get(Calendar.DAY_OF_MONTH));
        out.writeInt(date.get(Calendar.MONTH));
        out.writeInt(date.get(Calendar.YEAR));
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Reminder> CREATOR = new Parcelable.Creator<Reminder>() {
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    //constructor that takes a Parcel and gives you an object populated with it's values
    private Reminder(Parcel in) {
        //year, month, day, hour, minute
        date.set(in.readInt(),in.readInt(),in.readInt(),in.readInt(),in.readInt());
        notes = in.readString();
        title = in.readString();
    }
}