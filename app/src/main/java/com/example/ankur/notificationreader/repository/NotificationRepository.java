package com.example.ankur.notificationreader.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ankur.notificationreader.database.Notification;
import com.example.ankur.notificationreader.database.NotificationDao;
import com.example.ankur.notificationreader.database.NotificationDb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationRepository {

    private NotificationDao mWordDao;
    private List<Notification> allNotifications = new ArrayList<>();
    private static final String TAG = "ROOM_FETCH";
    private Context context;

    public NotificationRepository(final Context application) {
        this.context = application;
        final NotificationDb db = NotificationDb.getDatabase(application);
        mWordDao = db.notificationDao();
    }

    public NotificationRepository(final Context application, final Notification notification) {
        final NotificationDb db = NotificationDb.getDatabase(application);
        mWordDao = db.notificationDao();
        new insertAsyncTask(mWordDao).execute(notification);
    }

    public List<Notification> getAllNotifications(){
        this.fetchAllNotifications();
        return this.allNotifications;
    }


    private void fetchAllNotifications() {
        try {
            this.allNotifications = new getAllAsyncTask(mWordDao).execute().get();
        } catch (ExecutionException e) {
            Log.e(TAG, "ExecutionException");
        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException");
        }
    }

    private static class getAllAsyncTask extends AsyncTask<Void, Void, List<Notification>> {

        private NotificationDao mAsyncTaskDao;

        getAllAsyncTask(NotificationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Notification> doInBackground(final Void... params) {
            return mAsyncTaskDao.getAll();
        }
    }

    private static class insertAsyncTask extends AsyncTask<Notification, Void, Void> {

        private NotificationDao mAsyncTaskDao;

        insertAsyncTask(NotificationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Notification... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteAll() {
        final NotificationDb db = NotificationDb.getDatabase(this.context);
        mWordDao = db.notificationDao();
        new deleteAllsyncTask(mWordDao).execute();
    }

    private static class deleteAllsyncTask extends AsyncTask<Void, Void, Void> {

        private NotificationDao mAsyncTaskDao;

        deleteAllsyncTask(NotificationDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
