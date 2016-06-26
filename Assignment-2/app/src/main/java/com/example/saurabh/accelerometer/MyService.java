package com.example.saurabh.accelerometer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class MyService extends Service implements SensorEventListener{

    private static float x,y,z;
    final static boolean a = true;
    static int b = 0;
    static int ACCE_FILTER_DATA_MIN_TIME = 1000; // 1000ms
    long lastSaved = System.currentTimeMillis();
    final static String MY_ACTION = "MY_ACTION";
    Sensor accelerometer;
    SensorManager sm;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Accelerometer part
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        // Accelerometer end


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if ((System.currentTimeMillis() - lastSaved) > ACCE_FILTER_DATA_MIN_TIME) {
            lastSaved = System.currentTimeMillis();
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            Intent intent = new Intent();
            intent.setAction(MY_ACTION);

            intent.putExtra("DATAPASSED1",x);
            intent.putExtra("DATAPASSED2",y);
            intent.putExtra("DATAPASSED3",z);

            sendBroadcast(intent);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}


