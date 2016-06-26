package com.example.saurabh.project;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Pushups extends Activity implements SensorEventListener {

    TextView proxText;
    TextView countText;
    SensorManager sm;
    Sensor proxSensor;
    Button reset_button, done_button;
    float prev=0, curr=0;
    int count = 0,  count_p = 0;
    public static String  TABLE_NAME = "AppData";
    Mp3Player mp3;
    public static float BMR = 0, desiredcalories = 0;//new omkar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushups);

        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        proxSensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proxText = (TextView)findViewById(R.id.prox_textView);
        countText = (TextView)findViewById(R.id.count_textView);
        reset_button = (Button)findViewById(R.id.reset_button);
        done_button = (Button)findViewById(R.id.done_button);

        sm.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);

        try {
            //catch the table name sent by first activity and store it in string TABLE_NAME
            TABLE_NAME = getIntent().getStringExtra("table_name");
            String s = getIntent().getStringExtra("BMR");
            BMR = Float.parseFloat(s);
            System.out.println("pushups bmr = "+BMR);
            String r = getIntent().getStringExtra("des");
            desiredcalories = Float.parseFloat(r);
            //getIntent().getFloatExtra("BMR", BMR);// new omkar
            //getIntent().getFloatExtra("desiredcalories", desiredcalories);// new omkar
            //TableName.setText(TABLE_NAME);
            //Toast.makeText(MainActivity2.this, TABLE_NAME, Toast.LENGTH_SHORT).show();
        }catch(Exception e) {
            Toast.makeText(Pushups.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        reset_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        count_p = 0;
                        countText.setText(String.valueOf(0));
                    }
                }
        );

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                //pass the table name from current activity to next activity
                String count_string = String.valueOf(count_p);
                intent.putExtra("Pushups_done",count_string);
                intent.putExtra("table_name",TABLE_NAME);
                String t1 = ""+BMR;
                intent.putExtra("BMR",t1);
                String t = ""+desiredcalories;
                intent.putExtra("des",t);
                //intent.putExtra("BMR",BMR);//new omkar
                //intent.putExtra("desiredcalories", desiredcalories);//new omkar
                //Start the new activity
                startActivity(intent);
                finish();
                if(((GlobalState) Pushups.this.getApplication()).getMusicState()) {

                    mp3.cancel(true);
                }

            }
        });

        if(((GlobalState) Pushups.this.getApplication()).getMusicState()){
            mp3=new Mp3Player(getApplicationContext());
            mp3.execute("");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        proxText.setText(String.valueOf(event.values[0]));
        //countText.setText(String.valueOf(9999));
        if(count!=0) {
            curr = event.values[0];
            if (prev-curr > 2) {
                count_p++;
                countText.setText(String.valueOf(count_p));
                //countText.setText(count);
                // countText.setText(String.valueOf(curr+100));
            }
        }
        prev = event.values[0];
        count++;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        if(((GlobalState) Pushups.this.getApplication()).getMusicState()) {

            mp3.cancel(true);
        }
    }
}