package com.example.saurabh.project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    AlarmManager alarm_manager;
    TimePicker alarm_timePicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        this.context=this;

        //Initialize alarm manager
        alarm_manager= (AlarmManager)getSystemService(ALARM_SERVICE);

        //Initialize TimePicker
        alarm_timePicker = (TimePicker)findViewById(R.id.timePicker);

        //Initialize Text update box
        update_text=(TextView)findViewById(R.id.update_text);

        //Create an instance of calendar
        final Calendar calendar = Calendar.getInstance();

        final Intent my_intent = new Intent(this, Alarm_Receiver.class);

        //Initialize button
        Button start_alarm = (Button)findViewById(R.id.alarm_on);


        start_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Setting calendar insttance with hour and minute from timepicker
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarm_timePicker.getCurrentMinute());

                //get the string values of the hour and minutes
                int hour= alarm_timePicker.getCurrentHour();
                int minute= alarm_timePicker.getCurrentMinute();

                //Convert the int values to strings
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                if (hour>12){
                    hour_string=String.valueOf(hour-12);
                }

                if (minute<10){
                    minute_string= "0" + String.valueOf(minute);
                }


                set_alarm_text("Alarm set to: "+ hour_string + ":" + minute_string);

                //put in extr string into my_intent, to tell the clock that you pressed alarm on button
                my_intent.putExtra("extra","alarm on");

                //create a pending intent that delayed the specified calndar time
                pending_intent=PendingIntent.getBroadcast(AlarmActivity.this, 0, my_intent,PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pending_intent);



            }
        });


        Button stop_button = (Button) findViewById(R.id.alarm_off);
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                set_alarm_text("Alarm off");

                //cancel the alarm
                alarm_manager.cancel(pending_intent);

                // put extra string into my_intent, tells the clock alarm off
                my_intent.putExtra("extra","alarm off");

                //stop the ringtone
                sendBroadcast(my_intent);
            }
        });






    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i3= new Intent(this, MySettings.class);
        startActivity(i3);
        finish();
    }
}
