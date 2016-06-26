package com.example.saurabh.project;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Saurabh on 15-04-2016.
 */
public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;
    Intent intent_main_activity;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("LongLogTag")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // fetch the extra string values
        String state = intent.getExtras().getString("extra");

        Log.e("Ringtone state: extra is ", state);



        //converts the extra strings from intent
        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("Start ID is ", state);
                break;
            default:
                startId = 0;
                break;
        }


        //if-else conditions

        //if there is no music playing and user presses alarm_on
        //music should start playing
        if(!this.isRunning && startId == 1){
            Log.e("There is no music", "and start");
            //Create an instance of the media player
            media_song=MediaPlayer.create(this,R.raw.dove);
            media_song.start();

            this.isRunning = true;
            this.startId = 0;



            // set up an intent that goes to the main activity
            if(((GlobalState) RingtonePlayingService.this.getApplication()).getWeatherState()) {
                intent_main_activity = new Intent(this, secondpage.class);
            }else{
                intent_main_activity = new Intent(this, MainActivity.class);
            }

            //set up a pending intent
            PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, (int)System.currentTimeMillis(), intent_main_activity,0);

            //make the notification paramters
            Notification notification_popup  = new Notification.Builder(this)
                    .setContentTitle("Exercise time")
                    .setContentText("Common Boy..!!")
                    .setContentIntent(pending_intent_main_activity)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setAutoCancel(true)
                    .build();

            //Notifications, set up the notification service
            NotificationManager notify_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            //set up the notification start cmd
            notify_manager.notify(0,notification_popup);

        }
        //if there is music playing and the user pressed alarm off
        // music should stop playing
        else if(this.isRunning && startId ==0){
            Log.e("There is music", "and end");

            //stop ringtone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;

        }else if(!this.isRunning && startId==0){
            Log.e("There is no music", "and end");

            this.isRunning = false;
            this.startId = 0;

        }else if(this.isRunning && startId ==1){
            Log.e("There is music", "and start");

            this.isRunning = true;
            this.startId=1;
        }
        else{
            Log.e("stop ", "and start");

        }




        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }
}

