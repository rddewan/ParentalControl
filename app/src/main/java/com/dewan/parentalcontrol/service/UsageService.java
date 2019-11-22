package com.dewan.parentalcontrol.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.dewan.parentalcontrol.appstats.AppUsageStats;
import com.dewan.parentalcontrol.receiver.RestartServiceReceiver;

public class UsageService extends Service {
    private static final String TAG = "UsageService";
    public static boolean isServiceRunning = false;
    private static String STATUS;

    public UsageService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        STATUS  = intent.getStringExtra("STATUS");
        if (STATUS == null || STATUS.equals("START")){
            if (!isServiceRunning){
                AppUsageStats.getUsageStats(getApplicationContext());
                isServiceRunning = true;
                Log.e(TAG, "onStartCommand: " + isServiceRunning );
            }
        }
        else {
            onDestroy();
        }

        return START_REDELIVER_INTENT;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " + isServiceRunning );
        isServiceRunning = false;

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.e(TAG, "onTaskRemoved:");
        isServiceRunning = false;
        Intent intent = new Intent(this, RestartServiceReceiver.class);
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
