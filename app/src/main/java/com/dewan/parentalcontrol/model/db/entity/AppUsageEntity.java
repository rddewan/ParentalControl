package com.dewan.parentalcontrol.model.db.entity;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.dewan.parentalcontrol.BR;

import java.util.Comparator;

@Entity(tableName = "app_usages")
public class AppUsageEntity extends BaseObservable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "app_name")
    private String app_name;

    @ColumnInfo(name = "package_name")
    private String package_name;

    @ColumnInfo(name = "total_app_in_foreground_sec")
    private double totalAppInForegroundSec;
    @ColumnInfo(name = "total_app_in_foreground_min")
    private double totalAppInForegroundMin;
    @ColumnInfo(name = "total_app_in_foreground_hr")
    private double totalAppInForegroundHr;


    @Ignore
    private Drawable app_icon;

    @Ignore
    public AppUsageEntity() {
    }

    public AppUsageEntity(int id, String app_name, String package_name, double totalAppInForegroundSec) {
        this.id = id;
        this.app_name = app_name;
        this.package_name = package_name;
        this.totalAppInForegroundSec = totalAppInForegroundSec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public Drawable getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(Drawable app_icon) {
        this.app_icon = app_icon;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    @Bindable
    public double getTotalAppInForegroundSec() {
        return totalAppInForegroundSec;
    }


    public void setTotalAppInForegroundSec(double totalAppInForegroundSec) {
        this.totalAppInForegroundSec = totalAppInForegroundSec;
        notifyPropertyChanged(com.dewan.parentalcontrol.BR.totalAppInForegroundSec);
    }

    @Bindable
    public double getTotalAppInForegroundMin() {
        return totalAppInForegroundMin;
    }

    public void setTotalAppInForegroundMin(double totalAppInForegroundMin) {
        this.totalAppInForegroundMin = totalAppInForegroundMin;
        notifyPropertyChanged(com.dewan.parentalcontrol.BR.totalAppInForegroundMin);
    }

    @Bindable
    public double getTotalAppInForegroundHr() {
        return totalAppInForegroundHr;
    }

    public void setTotalAppInForegroundHr(double totalAppInForegroundHr) {
        this.totalAppInForegroundHr = totalAppInForegroundHr;
        notifyPropertyChanged(com.dewan.parentalcontrol.BR.totalAppInForegroundHr);
    }

    @BindingAdapter("android:text")
    public static void setText(TextView view, double value) {
        view.setText(Double.toString(value));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static double getText(TextView view) {
        return Double.parseDouble(view.getText().toString());
    }

    public static Comparator<AppUsageEntity> totalUsageTimeComparator = new Comparator<AppUsageEntity>() {
        @Override
        public int compare(AppUsageEntity t0, AppUsageEntity t1) {
            return Double.compare(t1.totalAppInForegroundSec,t0.totalAppInForegroundSec);
        }

    };

}
