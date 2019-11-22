package com.dewan.parentalcontrol.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dewan.parentalcontrol.service.UsageService;

public class RestartServiceReceiver extends BroadcastReceiver {
    private static final String TAG = "RestartServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive:");
        /*
       try{
           context.startService(new Intent(context, UsageService.class));
       }
       catch (Exception er){
           Log.e(TAG, "onReceive: " + er.getLocalizedMessage());
       }
         */
    }
}
