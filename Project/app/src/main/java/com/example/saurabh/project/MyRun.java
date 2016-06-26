package com.example.saurabh.project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MyRun extends AppCompatActivity implements SensorEventListener {

    private Button Gpsbutton, Donebutton;
    private TextView textloc, RunTime;
    private TextView textDistance;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location startingPosition;
    private static boolean firstLocation = true;
    private double lat1, lon1, lat2, lon2;
    private static double distance = 0;
    private float[] result = new float[3];
    long StartTime, TotalTime = 0;
    public static long Difference=0;
    int n=1;
    public static float BMR = 0, desiredcalories=0;
    public static int mode_clicked;

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    static final float ALPHA = 0.1f;
    private double output=0;
    public static String  TABLE_NAME = "AppData";
    long runningTime, walkingTime, cyclingtime =0;
    Mp3Player mp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_run);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        Gpsbutton = (Button) findViewById(R.id.GPSbutton);
        Donebutton = (Button) findViewById(R.id.Donebutton);
        textloc = (TextView) findViewById(R.id.TextLoc);
        textDistance = (TextView) findViewById(R.id.TextDistance);
        RunTime = (TextView) findViewById(R.id.RunTime);
        System.out.println("we are in onCreate");

        StartTime=0;
        TotalTime = 0;
        StartTime = new Date().getTime();
        Difference=0;

        if(((GlobalState) MyRun.this.getApplication()).getMusicState()){
            mp3=new Mp3Player(getApplicationContext());
            mp3.execute("");
        }


    }

    @Override
    protected void onStart(){
        super.onStart();

        //StartTime = System.nanoTime();//Initial time
        StartTime = new Date().getTime();

        RunTime.setText("0.0");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        System.out.println("we are in onstart");

        try {
            //catch the table name sent by first activity and store it in string TABLE_NAME
            TABLE_NAME = getIntent().getStringExtra("table_name");
            String s = getIntent().getStringExtra("BMR");
            BMR = Float.parseFloat(s);
            System.out.println("run bmr = "+BMR);
            String r = getIntent().getStringExtra("des");
            desiredcalories = Float.parseFloat(r);
            String ho = getIntent().getStringExtra("cycle");
            mode_clicked = Integer.parseInt(ho);
            //getIntent().getFloatExtra("BMR", BMR);// new omkar
            //getIntent().getFloatExtra("desiredcalories", desiredcalories);
            //TableName.setText(TABLE_NAME);
            //Toast.makeText(MainActivity2.this, TABLE_NAME, Toast.LENGTH_SHORT).show();
        }catch(Exception e) {
            Toast.makeText(MyRun.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                System.out.println("1");
                ConfigureButton();
                //return;
            }
        }else{
            System.out.println("config button called from onstart");
            ConfigureButton();
        }
        //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("location changed");

                if(firstLocation){
                    //StartTime = System.nanoTime();// start time when running starts
                    StartTime = new Date().getTime();
                    Toast.makeText(MyRun.this, "first location!!", Toast.LENGTH_SHORT).show();
                    System.out.println("first location!!");
                    firstLocation = false;
                    lat1 = location.getLatitude();
                    lon1 = location.getLongitude();
                    //firstLocation = false;
                }else{
                    //long temp = System.nanoTime();
                    long temp =  new Date().getTime();
                    TotalTime = temp - StartTime;
                    Difference += TotalTime;//Elapsed time
                    StartTime = temp;
                    RunTime.setText(""+(Difference/1000)+"sec");
                    System.out.println("time elapsed = "+(TotalTime/1000));
                    lat2 = location.getLatitude();
                    lon2 = location.getLongitude();
                    Location.distanceBetween(lat1, lon1, lat2, lon2, result);
                    distance += (long)result[0];
                    if(distance < 100000) {
                        if (distance > (n * 20)) {
                            n++;
                            if (((GlobalState) MyRun.this.getApplication()).getVibrationState()) {
                                Vibrator vibrator = (Vibrator) MyRun.this.getSystemService(Context.VIBRATOR_SERVICE);

                                try {
                                    vibrator.vibrate(500);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                        textDistance.setText("" + distance);
                    }
                    lat1 = lat2;
                    lon1 = lon2;
                }
               // textloc.setText("\n" + location.getLatitude() + ", " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                System.out.println("service finish");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };

        Gpsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("on");
                locationManager.requestLocationUpdates("gps", 2000, 0, locationListener);
                System.out.println("start");
                distance =0;
                firstLocation = true;

            }
        });

        Donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //unregister listener
                locationManager.removeUpdates(locationListener);
                locationManager = null;
                locationListener = null;
                double speed = distance/(Difference/1000);
                System.out.println("speed = "+ speed+" m/s");
                //traversing to previous activity and passing intent
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                //pass the table name from current activity to next activity
                String distance_string = ""+distance;
                System.out.println("distance in run= "+distance);
                intent.putExtra("Distance_travelled",distance_string);
                intent.putExtra("table_name",TABLE_NAME);
                if(mode_clicked == 0) {
                    String r = "" + runningTime;
                    intent.putExtra("runningtime", r);
                    System.out.println("running time in run = "+runningTime);
                    String w = "" + walkingTime;
                    intent.putExtra("walkingtime", w);
                    System.out.println("walking time in run = "+walkingTime);
                }else{
                    cyclingtime += runningTime + walkingTime;
                    String r = "" + cyclingtime;
                    intent.putExtra("cyclingtime", r);
                    System.out.println("cycle time in run = "+cyclingtime);
                }
                String t1 = ""+BMR;
                intent.putExtra("BMR",t1);
                String t = ""+desiredcalories;
                intent.putExtra("des",t);
                String sp = ""+speed;
                intent.putExtra("speed",sp);
                System.out.println("speed in run= "+speed);
                //intent.putExtra("desiredcalories", desiredcalories);
                //Start the new activity
                startActivity(intent);
                if(((GlobalState) MyRun.this.getApplication()).getMusicState()) {

                    mp3.cancel(true);
                }
                StartTime=0;
                TotalTime = 0;
                Difference=0;
                finish();
            }
        });

    }

    protected void onResume(){
        super.onResume();
        System.out.println("onResume");
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        System.out.println("onRequestPermissionsResult");
        switch (requestCode){
            case 10:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    ConfigureButton();
                    System.out.println("config button called from onstart");
                }
        }

    }

    private void ConfigureButton(){
        Gpsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("on");
                locationManager.requestLocationUpdates("gps", 2000, 0, locationListener);
                System.out.println("start");
                distance =0;
                firstLocation = true;

            }
        });

    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    protected double lowPass( double input) {
        output = output + ALPHA * (input - output);
        return output;
    }
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        TextView mode=(TextView)findViewById(R.id.mode);

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            //x=lowPass(x);
            //y=lowPass(y);
            //z=lowPass(z);

            double res=0;
            res=Math.sqrt(x*x+y*y+z*z);
            res=lowPass(res);
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 500) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                if((res>10)&&(res<13)){
                    if(mode_clicked == 1){
                        mode.setText("cycling");
                        cyclingtime = cyclingtime+diffTime;
                    }else {
                        mode.setText("Walking");
                    }
                    walkingTime = walkingTime+diffTime;
                    //System.out.println("WalkingTime: "+ walkingTime);
                }
                else if((res>13)){
                    if(mode_clicked == 1){
                        mode.setText("cycling");
                        cyclingtime = cyclingtime+diffTime;
                    }else {
                        mode.setText("Running");
                    }
                    runningTime = runningTime+diffTime;
                    //System.out.println("RunningTime: "+ runningTime);
                }

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(((GlobalState) MyRun.this.getApplication()).getMusicState()) {

            mp3.cancel(true);
        }

        finish();
    }
}