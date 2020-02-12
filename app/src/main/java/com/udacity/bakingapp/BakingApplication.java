package com.udacity.bakingapp;

import android.app.Application;
import android.content.Context;

public class BakingApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
