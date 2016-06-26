package com.example.saurabh.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MySettings extends AppCompatActivity {

    private RadioGroup radioMetricGroup;
    private RadioButton radioMetric;
    private Switch switchMusic;
    private Switch switchVibration;
    private Switch switchWeather;
    private Switch switchSocial;
    //private static MainActivity instance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instance=MySettings.this;
        setContentView(R.layout.activity_my_settings);


        switchMusic=(Switch) findViewById(R.id.music_option);

        switchVibration=(Switch) findViewById(R.id.vibration_option);
        switchWeather=(Switch) findViewById(R.id.weather_option);
        switchSocial=(Switch) findViewById(R.id.social_option);
        Button alarmPage = (Button)findViewById(R.id.tom);

        switchMusic.setChecked(((GlobalState) MySettings.this.getApplication()).getMusicState());
        switchVibration.setChecked(((GlobalState) MySettings.this.getApplication()).getVibrationState());
        switchWeather.setChecked(((GlobalState) MySettings.this.getApplication()).getWeatherState());
        switchSocial.setChecked(((GlobalState) MySettings.this.getApplication()).getSocialState());

        switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    try {
                        ((GlobalState) MySettings.this.getApplication()).setMusicState(true);
                        Toast.makeText(MySettings.this, "Music:"+((GlobalState) MySettings.this.getApplication()).getMusicState(), Toast.LENGTH_SHORT).show();
                    }catch(Exception e){
                        Log.e("Music",e.getMessage());
                    }

                } else {
                    ((GlobalState) MySettings.this.getApplication()).setMusicState(false);
                    Toast.makeText(MySettings.this, "Music:"+((GlobalState) MySettings.this.getApplication()).getMusicState(), Toast.LENGTH_SHORT).show();

                }
            }
        });
        switchVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    ((GlobalState)getApplication()).setVibrationState(true);
                    Toast.makeText(MySettings.this,"Vibration:"+((GlobalState) MySettings.this.getApplication()).getVibrationState(), Toast.LENGTH_SHORT).show();

                } else {
                    ((GlobalState) getApplication()).setVibrationState(false);
                    Toast.makeText(MySettings.this, "Vibration:"+((GlobalState) MySettings.this.getApplication()).getVibrationState(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        switchWeather.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    ((GlobalState)MySettings.this.getApplication()).setWeatherState(true);
                    Toast.makeText(MySettings.this,"WeatherSmart:"+((GlobalState) MySettings.this.getApplication()).getWeatherState(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this,radioSexButton.getText(), Toast.LENGTH_SHORT).show();

                } else {
                    ((GlobalState) MySettings.this.getApplication()).setWeatherState(false);
                    Toast.makeText(MySettings.this, "WeatherSmart:"+((GlobalState) MySettings.this.getApplication()).getWeatherState(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        switchSocial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    ((GlobalState)MySettings.this.getApplication()).setSocialState(true);
                    Toast.makeText(MySettings.this,"Social:"+((GlobalState) MySettings.this.getApplication()).getSocialState(), Toast.LENGTH_SHORT).show();

                } else {
                    ((GlobalState) MySettings.this.getApplication()).setSocialState(false);
                    Toast.makeText(MySettings.this, "Social:"+((GlobalState) MySettings.this.getApplication()).getSocialState(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        radioMetricGroup=(RadioGroup)findViewById(R.id.radio_group_metric);

        radioMetricGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                String met=new String("Km");

//                int selectedId=radioMetricGroup.getCheckedRadioButtonId();
                radioMetric = (RadioButton) findViewById(checkedId);
                //Toast.makeText(MainActivity.this,radioSexButton.getText(), Toast.LENGTH_SHORT).show();
                if (radioMetric.getText().equals(met)==true) {
                    ((GlobalState) MySettings.this.getApplication()).setMetricState(true);
                    Toast.makeText(MySettings.this, "Metric:"+((GlobalState) MySettings.this.getApplication()).getMetricState(), Toast.LENGTH_SHORT).show();


                } else {
                    ((GlobalState) MySettings.this.getApplication()).setMetricState(false);
                    Toast.makeText(MySettings.this, "Metric:"+((GlobalState) MySettings.this.getApplication()).getMetricState(), Toast.LENGTH_SHORT).show();

                }


            }
        });

        alarmPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3= new Intent(MySettings.this, AlarmActivity.class);
                startActivity(i3);
                finish();
            }
        });

//        ((GlobalState) this.getApplication()).setMusicState(true);

// get
//        boolean music = ((GlobalState) this.getApplication()).getMusicState();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(MySettings.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
