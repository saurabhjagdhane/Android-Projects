package com.example.saurabh.project;

import android.app.Application;
/**
 * Created by Saurabh on 23-04-2016.
 */
public class GlobalState extends Application {

    private boolean music_state=true;
    private boolean vibration_state=true;
    private boolean metric_state=true;
    private boolean weather_state=true;
    private boolean social_state=true;

    private double session_distance;
    private boolean music_play=true;



    public boolean getMusicState() {
        return music_state;
    }

    public void setMusicState(boolean music_state) {
        this.music_state = music_state;
    }

    public boolean getVibrationState() {
        return vibration_state;
    }

    public void setVibrationState(boolean vibration_state) {
        this.vibration_state = vibration_state;
    }

    public boolean getMetricState() {
        return metric_state;
    }

    public void setMetricState(boolean metric_state) {
        this.metric_state = metric_state;
    }

    public boolean getWeatherState() {
        return weather_state;
    }

    public void setWeatherState(boolean weather_state) {
        this.weather_state = weather_state;
    }

    public boolean getSocialState() {
        return social_state;
    }
    public void setSocialState(boolean social_state) {
        this.social_state = social_state;
    }

    public void setSessionDistance(double session_distance) {
        this.session_distance = session_distance;
    }
    public double getSessionDistance() {
        return session_distance;
    }

    public boolean getMusicPlay() {
        return music_play;
    }
    public void setMusicPlay(boolean music_play) {
        this.music_play = music_play;
    }


}