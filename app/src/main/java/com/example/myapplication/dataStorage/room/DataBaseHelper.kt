package com.example.myapplication.dataStorage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.dataStorage.room.properties.PropertiesDao
import com.example.myapplication.dataStorage.room.properties.PropertyEntity
import com.example.myapplication.dataStorage.room.propertydetails.PropertyDetailsDao
import com.example.myapplication.dataStorage.room.propertydetails.PropertyDetailsEntity
import com.example.myapplication.dataStorage.room.user.UserDAO
import com.example.myapplication.dataStorage.room.user.UserEntity

@Database(
    entities = [UserEntity::class, PropertyEntity::class, PropertyDetailsEntity::class],
    version = 1
)

abstract class DataBaseHelper : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun propertiesDao(): PropertiesDao
    abstract fun propertyDetails(): PropertyDetailsDao
}