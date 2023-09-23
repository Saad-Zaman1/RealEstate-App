package com.example.myapplication.dataStorage.room.propertydetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.example.myapplication.dataStorage.room.user.UserEntity

@Dao
interface PropertyDetailsDao {
    @Insert()
    suspend fun insertProperty(propertyDetails: PropertyDetailsEntity): Long

    @Update
    suspend fun updateProperty(propertyDetails: PropertyDetailsEntity)
}