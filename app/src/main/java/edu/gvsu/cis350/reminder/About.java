package edu.gvsu.cis350.reminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView newtext = (TextView) findViewById(R.id.textView3);
        newtext.setTextSize(18);
        newtext.setText("This is a Reminder app called Remind-R.\n" +
                "Created in Spring of 2016\n" +
                "Created by four students in a CIS 350 class.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Kevin\n" +
                "Andrew\n" +
                "Alex\n" +
                "Rabeeb");

    }
}
