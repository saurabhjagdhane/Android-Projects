package com.example.saurabh.project.service;

import com.example.saurabh.project.data.Channel;

/**
 * Created by Saurabh on 21-02-2016.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
