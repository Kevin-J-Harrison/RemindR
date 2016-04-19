package edu.gvsu.cis350.reminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (ViewReminders.getAppTheme()) {
            case 0:
                this.setTheme(R.style.AppTheme_NoActionBar);
                break;
            case 1:
                this.setTheme(R.style.Flame);
                break;
            case 2:
                this.setTheme(R.style.Forest);
                break;
            case 3:
                this.setTheme(R.style.Sunrise);
                break;
            case 4:
                this.setTheme(R.style.Slate);
                break;
            case 5:
                this.setTheme(R.style.TieDye);
                break;
        }

        setContentView(R.layout.activity_settings);
        Button GVButton = (Button) findViewById(R.id.GVThemeButton);
        Button flameButton = (Button) findViewById(R.id.flameThemeButton);
        Button forestButton = (Button) findViewById(R.id.forestThemeButton);
        Button royalButton = (Button) findViewById(R.id.royalThemeButton);
        Button slateButton = (Button) findViewById(R.id.slateThemeButton);
        Button tiedyeButton = (Button) findViewById(R.id.tiedyeThemeButton);

        Button saveSettingsButton = (Button) findViewById(R.id.save_settings);



        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Settings.this, ViewReminders.class);
                Settings.this.startActivity(myIntent);
            }
        });





        GVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewReminders.setAppTheme(0);
                Context context = getApplicationContext();
                CharSequence text = "Theme has been set to GV Blue.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        flameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewReminders.setAppTheme(1);
                Context context = getApplicationContext();
                CharSequence text = "Theme has been set to Flame.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        forestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ViewReminders.setAppTheme(2);
                Context context = getApplicationContext();
                CharSequence text = "Theme has been set to Forest.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        royalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ViewReminders.setAppTheme(3);
                Context context = getApplicationContext();
                CharSequence text = "Theme has been set to Sunrise.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        slateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {ViewReminders.setAppTheme(4);
                Context context = getApplicationContext();
                CharSequence text = "Theme has been set to Slate.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        tiedyeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {ViewReminders.setAppTheme(5);
                Context context = getApplicationContext();
                CharSequence text = "Theme has been set to Tie Dye.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });



    }

}
