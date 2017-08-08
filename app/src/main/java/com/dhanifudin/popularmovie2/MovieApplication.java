package com.dhanifudin.popularmovie2;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by dhanifudin on 8/4/17.
 */

public class MovieApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

}
