package com.dewan.parentalcontrol.repository;

import android.app.Application;
import android.app.usage.UsageStats;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dewan.parentalcontrol.db.AppDatabase;
import com.dewan.parentalcontrol.db.dao.AppUsageDao;
import com.dewan.parentalcontrol.db.entity.AppUsageEntity;
import com.dewan.usagestatshelper.AppUsageStatsProperty;
import com.dewan.usagestatshelper.UsageStatsHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;

public class AppUsageStatsRepository {
    private static final String TAG = "AppUsageStatsRepository";

    private static MutableLiveData<Double> maxTimeUsedSec  = new MutableLiveData<>();
    private static MutableLiveData<Double> maxTimeUsedMin = new MutableLiveData<>();
    private static MutableLiveData<Double> maxTimeUsedHr = new MutableLiveData<>();
    private static MutableLiveData<Drawable> appIcon = new MutableLiveData<>();


    public AppUsageStatsRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);

        new GetAppUsageStatsTask(application).execute();
    }

    public MutableLiveData<Double> getMaxTimeUsedSec(){
        return maxTimeUsedSec;
    }

    public MutableLiveData<Double> getMaxTimeUsedMin(){
        return maxTimeUsedMin;
    }

    public MutableLiveData<Double> getMaxTimeUsedHr(){
        return maxTimeUsedHr;
    }

    public LiveData<Drawable> getAppIcon(){
        return appIcon;
    }

    private static class GetAppUsageStatsTask extends AsyncTask<Void,Void,Double> {
        private double totalTime;
        private Context context;

        public GetAppUsageStatsTask(Context context) {
            this.context = context;
        }

        @Override
        protected Double doInBackground(Void... voids) {
            Log.e(TAG, "doInBackground: ");
            try {
                UsageStatsHelper.setPackageManager(context.getPackageManager());
                SortedMap<Long, UsageStats> mySortedMap = UsageStatsHelper.getForegroundApp(context);
                if (!mySortedMap.isEmpty()) {
                    ArrayList<AppUsageStatsProperty> appStatsLists = UsageStatsHelper.getAppUsageStatsList(mySortedMap);
                    //sort array list
                    Collections.sort(appStatsLists,AppUsageStatsProperty.totalUsageTimeComparator);
                    AppUsageStatsProperty property = appStatsLists.get(0);
                    Log.e(TAG, "MAX: " + property.getTotalTimeInForeground());
                    totalTime = property.getTotalTimeInForeground();
                    appIcon.postValue(property.getAppIcon());

                }
            } catch (Exception er) {
                Log.e(TAG, Objects.requireNonNull(er.getLocalizedMessage()));
            }

            return totalTime;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            Log.e(TAG, "onPostExecute: " + aDouble);
            maxTimeUsedSec.setValue(aDouble/1000);
            maxTimeUsedMin.setValue(aDouble/1000/60);
            maxTimeUsedHr.setValue(aDouble/1000/60/60);
        }
    }
}
