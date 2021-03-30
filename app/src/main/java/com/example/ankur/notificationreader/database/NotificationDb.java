package com.example.ankur.notificationreader.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Notification.class}, version = 2)
    public abstract class NotificationDb extends RoomDatabase {

        public abstract NotificationDao notificationDao();
        private static volatile NotificationDb INSTANCE;

        public static NotificationDb getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (NotificationDb.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                NotificationDb.class, "notification_db")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }
