package com.dewan.parentalcontrol.model.db;

import android.app.usage.UsageStats;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dewan.parentalcontrol.model.db.dao.AppUsageDao;
import com.dewan.parentalcontrol.model.db.entity.AppUsageEntity;
import com.dewan.parentalcontrol.model.db.entity.InstalledAppEntity;
import com.dewan.usagestatshelper.AppUsageStatsProperty;
import com.dewan.usagestatshelper.UsageStatsHelper;

import java.util.ArrayList;
import java.util.Objects;
import java.util.SortedMap;

@Database(entities = {AppUsageEntity.class, InstalledAppEntity.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";

    public  abstract AppUsageDao appUsageDao();
    private static  AppDatabase instance;
    private static Context mContext;

    public static synchronized AppDatabase getInstance(Context context){
        Log.e(TAG, "getInstance");
        mContext = context;
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,"parental_db")
                    .fallbackToDestructiveMigrationFrom()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialDataTask(instance).execute();
        }
    };

    private static class InitialDataTask extends AsyncTask<Void,Void,Void>{
        private AppUsageDao appUsageDao;

        private InitialDataTask(AppDatabase appDatabase) {
            Log.e(TAG, "doInBackground" );
            this.appUsageDao = appDatabase.appUsageDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                SortedMap<Long, UsageStats> mySortedMap = UsageStatsHelper.getForegroundAppDaily(mContext);
                if (!mySortedMap.isEmpty()) {
                    ArrayList<AppUsageStatsProperty> appStatsLists = UsageStatsHelper.getAppUsageStatsList(mySortedMap);
                    for (int i = 0; i < appStatsLists.size(); i++){
                        AppUsageStatsProperty statsProperty = new AppUsageStatsProperty();
                        statsProperty = appStatsLists.get(i);
                        AppUsageEntity appUsageEntity = new AppUsageEntity();
                        appUsageEntity.setPackage_name(statsProperty.getPackageName());
                        appUsageEntity.setTotalAppInForegroundSec(statsProperty.getTotalTimeInForeground());

                        appUsageDao.add(appUsageEntity);
                        Log.e(TAG, "doInBackground: New record added" );

                    }

                }
            } catch (Exception er) {
                Log.e(TAG, Objects.requireNonNull(er.getLocalizedMessage()));
            }

            return null;
        }
    }

}
