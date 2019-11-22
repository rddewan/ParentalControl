package com.dewan.parentalcontrol.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dewan.parentalcontrol.db.entity.InstalledAppEntity;

import java.util.List;

@Dao
public interface InstalledAppDao {

    @Insert
    public int insert(InstalledAppEntity entity);

    @Update int update(InstalledAppEntity entity);

    @Query("delete from installed_app")
    void delete();

    @Query("select * from installed_app")
    List<InstalledAppEntity> getInstalledApp();


}
