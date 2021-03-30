package com.example.ankur.notificationreader.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM notification_table")
    List<Notification> getAll();

    @Insert
    void insert(Notification notification);

    @Query("DELETE FROM notification_table")
    void deleteAll();

}