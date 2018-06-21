package org.jpmc.demo.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.jpmc.demo.utils.KioskModeUtil;
import org.jpmc.demo.utils.KioskService;
import org.jpmc.demo.utils.PrefUtils;
import org.jpmc.demo.R;


public class MainActivity extends Activity {

    private static boolean wasServiceStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Kiosk", "OnCREATE1");
        setContentView(R.layout.activity_main);

        PrefUtils.setKioskModeActive(true, getApplicationContext());
        if (KioskModeUtil.isMyLauncherDefault(getApplicationContext()) && !PrefUtils.isKioskModeActive(this)) {
            getPackageManager().clearPackagePreferredActivities(getPackageName());
        }


        if (!wasServiceStarted) {
            startKioskService();
            wasServiceStarted = true;
        }

        ImageView lock = findViewById(R.id.logo);


        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PrefUtils.setKioskModeActive(false, getApplicationContext());
                KioskModeUtil.removeStatusbarOverlay(getApplicationContext());
                KioskModeUtil.cancelDevicePolicy(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Please set the regular Home Screen as default.", Toast.LENGTH_LONG).show();
                KioskModeUtil.triggerLauncherChooser(getApplicationContext());
                finish();
            }
        });

    }



    protected void onPause() {
        super.onPause();
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
//        boolean kioskModeActive = PrefUtils.isKioskModeActive(this);
//        if (kioskModeActive) {
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    KioskModeUtil.handleKioskMode(getApplicationContext());
//                }
//            }, 300);
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!KioskModeUtil.isMyLauncherDefault(getApplicationContext()) && PrefUtils.isKioskModeActive(this)) {
            Toast.makeText(this, "Please set the Kiosk-App as default.", Toast.LENGTH_LONG).show();
            KioskModeUtil.triggerLauncherChooser(getApplicationContext());
        }

        if (PrefUtils.isKioskModeActive(getApplicationContext())) {
            KioskModeUtil.preventStatusBarExpansion(getApplicationContext());
            KioskModeUtil.applyDevicePolicy(getApplicationContext());
        } else {
            KioskModeUtil.removeStatusbarOverlay(getApplicationContext());
            KioskModeUtil.cancelDevicePolicy(getApplicationContext());
        }


    }


    private void startKioskService() { // ... and this method
        startService(new Intent(this, KioskService.class));
    }


    @Override
    public void onBackPressed() {
        // nothing to do here
        // … really
    }


}

