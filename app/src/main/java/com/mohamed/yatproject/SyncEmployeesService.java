package com.mohamed.yatproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class SyncEmployeesService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Get users from database
        // Add users name in file
        // Upload file

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
