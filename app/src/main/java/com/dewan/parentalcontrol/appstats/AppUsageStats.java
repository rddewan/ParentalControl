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
    private static long sumTime = 0,previousTime = 0;

    public static void getUsageStats(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (UsageService.isServiceRunning) {
                    try {
                        long appUsedTime,totalTimeUsed;
                        SortedMap<Long, UsageStats> mySortedMap = UsageStatsHelper.getForegroundApp(context);
                        if (!mySortedMap.isEmpty()) {
                            long currentTime = System.currentTimeMillis();
                            currentApp = Objects.requireNonNull(mySortedMap.get(mySortedMap.lastKey())).getPackageName();
                            appUsedTime = Objects.requireNonNull(mySortedMap.get(mySortedMap.lastKey())).getLastTimeUsed();
                            totalTimeUsed = Objects.requireNonNull(mySortedMap.get(mySortedMap.lastKey())).getTotalTimeInForeground() / 1000 / 60;
                            sumTime = (currentTime - appUsedTime) / 1000;

                            if (sumTime == 0) {
                                if (previousTime > 0){
                                    if (previousApp.equals(currentApp)){
                                        sumTime = previousTime + 1;
                                    }
                                    Log.e(TAG, "Insert To DB: " + previousApp + ":" + previousTime);
                                    Log.e(TAG, "CurrentApp: " + currentApp + " TimeUsed: " + sumTime + " PreviousApp:" + previousApp);
                                }

                            } else {
                                Log.e(TAG, "CurrentApp: " + currentApp + " TotalTimeUsed: " + totalTimeUsed );
                                Log.e(TAG, "CurrentApp: " + currentApp + " TimeUsed: " + sumTime + " PreviousApp:" + previousApp);
                            }

                        }
                        previousApp = currentApp;
                        previousTime = sumTime;

                        Thread.sleep(1000);
                    } catch (Exception er) {
                        Log.e(TAG, Objects.requireNonNull(er.getLocalizedMessage()));
                    }

                }


            }
        }).start();

    }
}
