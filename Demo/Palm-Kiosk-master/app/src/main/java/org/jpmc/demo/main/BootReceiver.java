package org.jpmc.demo.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.jpmc.demo.ui.MainActivity;

/**
 * Created by Andreas Schrade on 17.09.2015.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, MainActivity.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}
