package org.jpmc.demo.main;

import android.app.Application;

/**
 * Created by Andreas Schrade on 17.09.2015.
 */
public class LauncherDemoapp extends Application {

    private LauncherDemoapp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
