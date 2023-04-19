package com.roomdb.testproject.ui.fragments.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    Worker(appContext, params) {

    companion object {
        const val WORK_NAME = "com.roomdb.testproject.ui.fragments.workmanager.RefreshDataWorker"
    }
    override fun doWork(): Result {

        try {
            val currentTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val currentTimeString = dateFormat.format(currentTime)

            Log.d("deepak", currentTimeString)

        } catch (e: Exception) {
            Log.d("deepak", e.localizedMessage?: "Exception")
            return Result.retry()
        }

        return Result.success()
    }
}
