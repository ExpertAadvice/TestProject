package com.roomdb.testproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passTable")
data class PassManager(
    @PrimaryKey
    val passKey: String,
    val passValue: String,
    val desc: String? = null
)
