package com.example.ankur.notificationreader.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification_table")
public class Notification {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "appPackage")
    private String appPackage;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "message")
    private String message;


    public Notification(final String appPackage, final String title, final String message) {
        this.appPackage = appPackage;
        this.title = title;
        this.message = message;
    }

    public int getUid() {
        return uid;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setAppPackage(final String appPackage) {
        this.appPackage = appPackage;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
