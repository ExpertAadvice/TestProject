package com.roomdb.testproject

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.*
import com.roomdb.testproject.ui.fragments.workmanager.RefreshDataWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MyApp : Application() {

    companion object {
        const val channelId = "MyChannel"
    }

    override fun onCreate() {
        super.onCreate()

        // Location foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val locationServiceChannel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(locationServiceChannel)
        }


        // Notification foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId, "service example",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(serviceChannel)
        } else {
            // Android version below SDK 26 or OREO
        }

        delayedInit()
    }

    /**
     * Setup WorkManager background job to perform some action based on time frame.
     * Keep this section in code if you target Min SDK 24 below
     * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
     * setRequiresDeviceIdle(true)
     * }
     */
    private fun delayedInit() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}