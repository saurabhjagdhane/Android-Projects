package com.example.saurabh.project;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Saurabh on 23-04-2016.
 */
public class Mp3Player extends AsyncTask<String, Void, String> {
    Context c;
    Uri p;
    int length=0;
    public Mp3Player(Context context)
    {
        c= context;
    }

    MediaPlayer mPlayer=null;

    @Override
    protected String doInBackground(String... params) {
        mPlayer.start();
        while(mPlayer.isPlaying()==true) {
            if(isCancelled())
                break;

        }
/*        while(mPlayer.isPlaying()==true){
            if(1<2){///Need to access accel from MainActivity
                mPlayer.pause();
                length=mPlayer.getCurrentPosition();
            }
            else{
                mPlayer.seekTo(length);
                mPlayer.start();
            }
        }
*/        return "OK";

    }

    @Override
    protected void onPostExecute(String result) {
        // might want to change "executed" for the returned string passed
        // into onPostExecute() but that is upto you
        mPlayer.stop();
        mPlayer.release();
        Toast.makeText(c,"Finished",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {

        String path= Environment.getExternalStorageDirectory().getPath()+ "/alarms/Fireflies.mp3";
        p= Uri.parse(path);
        Log.e("Value", path);
        mPlayer= MediaPlayer.create(c,p);

        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        Toast.makeText(c, "Music Started", Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onCancelled(){
        mPlayer.stop();
        mPlayer.release();
    }

    @Override
    protected void onProgressUpdate(Void... values) {}
}
