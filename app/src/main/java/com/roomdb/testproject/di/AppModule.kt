package com.roomdb.testproject.di

import android.content.Context
import com.roomdb.testproject.data.repository.*
import com.roomdb.testproject.data.room.PasswordDataBase
import com.roomdb.testproject.domain.repository.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesRoomRepository(db: PasswordDataBase): RoomRepository {
        return RoomRepositoryImpl(db.passDao())
    }

    @Provides
    @Singleton
    fun providesPasswordDb(@ApplicationContext context: Context) : PasswordDataBase {
        return PasswordDataBase.getDatabase(context)
    }

}