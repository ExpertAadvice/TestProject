package com.roomdb.testproject.data.repository

import com.roomdb.testproject.data.room.PassDao
import com.roomdb.testproject.domain.model.PassManager
import com.roomdb.testproject.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow

class RoomRepositoryImpl (
    private val dao: PassDao
): RoomRepository {

    override suspend fun addPassword(passManager: PassManager) {
        dao.addPass(passManager)
    }

    override suspend fun getPasswordList(): Flow<List<PassManager>> {
        return dao.getPassList()
    }

    override suspend fun deletePassByKey(passManager: PassManager): Int{
        return dao.deletePassByKey(passManager)
    }

    override suspend fun updatePass(passManager: PassManager): Int {
        return dao.updatePass(passManager)
    }
}