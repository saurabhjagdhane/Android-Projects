package com.example.saurabh.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Saurabh on 15-04-2016.
 */
public class Alarm_Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("We are in the Rx", "Okkkk");

        // fetch extra string from intent
        String get_your_string = intent.getExtras().getString("extra");

        Log.e("What is the key?", get_your_string);

        //Create an intent to ringtone service
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        //pass the extra string from main Activity to the ringtone playing service
        service_intent.putExtra("extra", get_your_string);

        //start the ringtone service
        context.startService(service_intent);

    }
}

