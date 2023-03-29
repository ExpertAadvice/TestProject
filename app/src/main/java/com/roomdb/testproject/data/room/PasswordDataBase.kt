package com.roomdb.testproject.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.roomdb.testproject.domain.model.PassManager

const val DATABASE_NAME = "password_database581"

@Database(entities = [PassManager::class], version = 1, exportSchema = false)
abstract class PasswordDataBase: RoomDatabase() {

    abstract fun passDao(): PassDao

    companion object {

        private var instance: PasswordDataBase? = null

        fun getDatabase(context: Context): PasswordDataBase {
            if (instance == null) {
                synchronized(this) {
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context): PasswordDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                PasswordDataBase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}