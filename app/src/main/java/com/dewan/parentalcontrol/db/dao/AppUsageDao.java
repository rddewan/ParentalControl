package com.dewan.parentalcontrol.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dewan.parentalcontrol.db.entity.AppUsageEntity;

import java.util.List;
@Dao
public interface AppUsageDao {
    @Insert
    long add(AppUsageEntity appUsageEntity);

    @Update
    int update(AppUsageEntity appUsageEntity);

    @Query("select * from app_usages")
    List<AppUsageEntity> getAppUsages();

    @Query("select * from app_usages where package_name == :packageName")
    AppUsageEntity getAppUsage(int packageName);

    @Query("DELETE FROM app_usages")
    int delete();

}
