package com.roomdb.testproject

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    companion object {
        const val channelId = "MyChannel"
    }

    override fun onCreate() {
        super.onCreate()

        // Location foreground service
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
                channelId,"service example",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(serviceChannel)
        } else {
            // Android version below SDK 26 or OREO
        }
    }
}