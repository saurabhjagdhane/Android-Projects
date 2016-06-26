package com.example.saurabh.project.data;

import org.json.JSONObject;

/**
 * Created by Saurabh on 21-02-2016.
 */
public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {

        temperature = data.optString("temperature");

    }
}
