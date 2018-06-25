package com.madfooat.weather.android.demo.app.application;

import android.app.Application;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

/**
 *
 * @author gbarham
 */
public class WeatherApplication extends Application {
    private static AsyncHttpClient httpClient = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.httpClient = new AsyncHttpClient();
        addHeaders();
    }

    private void addHeaders(){
        this.httpClient.addHeader("Cache-Control","no-cache");
    }

    public static AsyncHttpClient getHttpClient() {
        return httpClient;
    }
}
