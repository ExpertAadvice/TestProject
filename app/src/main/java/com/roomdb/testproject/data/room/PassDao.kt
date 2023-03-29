package com.roomdb.testproject.data.room

import androidx.room.*
import com.roomdb.testproject.domain.model.PassManager
import kotlinx.coroutines.flow.Flow

@Dao
interface PassDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addPass(passManager: PassManager)

    @Query("Select * from passTable ORDER BY passKey DESC")
    fun getPassList(): Flow<List<PassManager>>

    @Update
    suspend fun updatePass(passManager: PassManager) : Int

//    @Query("Delete FROM passTable where passKey LIKE :passKey")
//    suspend fun deletePassByKey(passKey: String): Int

    @Delete
    suspend fun deletePassByKey(passManager: PassManager): Int

}