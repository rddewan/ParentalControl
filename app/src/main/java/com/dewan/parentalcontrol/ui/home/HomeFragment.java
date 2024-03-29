package com.dewan.parentalcontrol.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dewan.parentalcontrol.R;
import com.dewan.parentalcontrol.databinding.FragmentHomeBinding;
import com.dewan.parentalcontrol.model.db.entity.AppUsageEntity;
import com.dewan.usagestatshelper.UsageStatsHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.DecimalFormat;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private static final int USAGE_STATS_PERMISSION = 12345;
    private View view;
    private FragmentHomeBinding fragmentHomeBinding;
    private HomeViewModel homeViewModel;
    private AppUsageEntity appUsageEntity = new AppUsageEntity();
    private static DecimalFormat decimalFormat;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        decimalFormat = new DecimalFormat("#.##");
        fragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        view = fragmentHomeBinding.getRoot();

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        checkUsageStatsPermission();

        return view;
    }

    private void getUsageStats(){
        homeViewModel.getSec().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                String data = decimalFormat.format(aDouble);
                appUsageEntity.setTotalAppInForegroundSec(Double.parseDouble(data));
                //fragmentHomeBinding.setAppUsageStatsHome(appUsageEntity);
                Log.e(TAG, "onChanged Sec: " + appUsageEntity.getTotalAppInForegroundSec() );
            }
        });

        homeViewModel.getMin().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                String data = decimalFormat.format(aDouble);
                appUsageEntity.setTotalAppInForegroundMin(Double.parseDouble(data));
                //fragmentHomeBinding.setAppUsageStatsHome(appUsageEntity);
                Log.e(TAG, "onChanged Min: " + aDouble );
            }
        });

        homeViewModel.getHr().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                String data = decimalFormat.format(aDouble);
                appUsageEntity.setTotalAppInForegroundHr(Double.parseDouble(data));
                //fragmentHomeBinding.setAppUsageStatsHome(appUsageEntity);
                Log.e(TAG, "onChanged Hr: " + aDouble );
            }
        });

        homeViewModel.getAppIcon().observe(this, new Observer<Drawable>() {
            @Override
            public void onChanged(Drawable drawable) {
                Log.e(TAG, "onChanged: App Icon" );
                appUsageEntity.setApp_icon(drawable);
                fragmentHomeBinding.setAppUsageStatsHome(appUsageEntity);

            }
        });
    }

    private void checkUsageStatsPermission() {
        boolean isPermissionGranted = UsageStatsHelper.getAppUsageStatsPermission(getActivity());
        if (!isPermissionGranted) {
            askForUsageStatsPermission();
        } else {
            getUsageStats();
        }
    }

    private void askForUsageStatsPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setTitle("Permission");
        builder.setMessage("Please Provide Usage Stats App Permission");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivityForResult(intent, USAGE_STATS_PERMISSION);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                checkUsageStatsPermission();
            }
        });
        builder.show();
    }
}