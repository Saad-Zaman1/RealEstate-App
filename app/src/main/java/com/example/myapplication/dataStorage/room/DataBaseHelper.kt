package com.example.myapplication.dataStorage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.dataStorage.room.user.UserDAO
import com.example.myapplication.dataStorage.room.user.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)

abstract class DataBaseHelper : RoomDatabase() {
    abstract fun userDao(): UserDAO
}