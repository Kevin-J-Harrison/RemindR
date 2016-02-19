package edu.gvsu.cis350.reminder;

import java.util.Date;

/**

 * Created by Kevin Harrison on 2/19/2016.

 */

public class Reminder {

    private String title;

    private Date date;

    private String notes;

    public Reminder (){

        title = " ";

        notes = " ";

    }

    public void setTitle(String titleP){

        title = titleP;

    }

    public void setDate(Date reminderDate){

        date = reminderDate;

    }

    public void setNotes(String notesP){

        notes = notesP;

    }

    public String getTitle(){

        return title;

    }

    public Date getDate(){

        return date;

    }

    public String getNotes(){

        return notes;

    }

}