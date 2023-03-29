package com.roomdb.testproject.domain.repository

import com.roomdb.testproject.domain.model.PassManager
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    suspend fun addPassword(passManager: PassManager)

    suspend fun getPasswordList(): Flow<List<PassManager>>

    suspend fun deletePassByKey(passManager: PassManager): Int

    suspend fun updatePass(passManager: PassManager): Int

}