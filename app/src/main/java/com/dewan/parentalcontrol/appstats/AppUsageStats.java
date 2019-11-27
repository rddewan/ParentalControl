package com.dewan.parentalcontrol.appstats;

import android.app.Application;
import android.app.usage.UsageStats;
import android.content.Context;
import android.util.Log;

import com.dewan.parentalcontrol.service.UsageService;
import com.dewan.usagestatshelper.UsageStatsHelper;

import java.util.Objects;
import java.util.SortedMap;

public class AppUsageStats {
    private static final String TAG = "AppUsageStats";
    private static String currentApp = "", previousApp = "";

    /*
        this method will get the current foreground app
    */
    public static void getUsageStats(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (UsageService.isServiceRunning) {
                    try {

                        SortedMap<Long, UsageStats> mySortedMap = UsageStatsHelper.getForegroundApp(context);
                        if (!mySortedMap.isEmpty()) {
                            currentApp = Objects.requireNonNull(mySortedMap.get(mySortedMap.lastKey())).getPackageName();
                            Log.e(TAG, "currentApp: " + currentApp );
                        }

                        Thread.sleep(1000);

                    } catch (Exception er) {
                        Log.e(TAG, Objects.requireNonNull(er.getLocalizedMessage()));
                    }

                }


            }
        }).start();

    }
}
