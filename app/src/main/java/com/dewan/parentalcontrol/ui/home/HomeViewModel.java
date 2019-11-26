package com.dewan.parentalcontrol.ui.home;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dewan.parentalcontrol.repository.AppUsageStatsRepository;

public class HomeViewModel extends AndroidViewModel {

    AppUsageStatsRepository appUsageStatsRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        appUsageStatsRepository = new AppUsageStatsRepository(application);
    }

    public LiveData<Double> getSec() {
        return appUsageStatsRepository.getMaxTimeUsedSec();
    }

    public LiveData<Double> getMin() {
        return appUsageStatsRepository.getMaxTimeUsedMin();
    }

    public LiveData<Double> getHr() {
        return appUsageStatsRepository.getMaxTimeUsedHr();
    }

    public LiveData<Drawable> getAppIcon(){
        return appUsageStatsRepository.getAppIcon();
    }
}