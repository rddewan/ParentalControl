package com.dewan.parentalcontrol.db.entity;

import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity(tableName = "installed_app")
public class InstalledAppEntity  {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "device_id")
    private String deviceId;

    @ColumnInfo(name = "app_name")
    private String appName;

    @ColumnInfo( name ="package_name")
    private String packageName;

    @Ignore
    private Drawable appIcon;

    @ColumnInfo(name = "app_icon")
    private String appIconBase64;

    @ColumnInfo(name = "version_name")
    private String versionName;

    @ColumnInfo(name = "version_code")
    private int versionCode;

    @ColumnInfo(name = "system_app")
    private boolean isSystemApp;

    @ColumnInfo(name = "is_locked")
    private boolean isLocked;

    @Ignore
    private String appExistMsg;

    @Ignore
    public InstalledAppEntity() {
    }

    public InstalledAppEntity(String deviceId, String appName, String packageName, String appIconBase64, String versionName, int versionCode, boolean isSystemApp, boolean isLocked) {
        this.deviceId = deviceId;
        this.appName = appName;
        this.packageName = packageName;
        this.appIconBase64 = appIconBase64;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.isSystemApp = isSystemApp;
        this.isLocked = isLocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppIconBase64() {
        return appIconBase64;
    }

    public void setAppIconBase64(String appIconBase64) {
        this.appIconBase64 = appIconBase64;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public void setSystemApp(boolean systemApp) {
        isSystemApp = systemApp;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public String getAppExistMsg() {
        return appExistMsg;
    }

    public void setAppExistMsg(String appExistMsg) {
        this.appExistMsg = appExistMsg;
    }

    public static Comparator<InstalledAppEntity> appNameComparator = new Comparator<InstalledAppEntity>() {
        @Override
        public int compare(InstalledAppEntity t1, InstalledAppEntity t2) {
            String ascending = t1.getAppName();
            String descending = t2.getAppName();
            return ascending.compareTo(descending);
        }
    };
}
