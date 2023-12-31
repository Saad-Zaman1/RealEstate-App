package com.example.myapplication.dataStorage.room.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userAccounts")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var username: String,
    val phone: String,
    val email: String,
    val password: String,
)
