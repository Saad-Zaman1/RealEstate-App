package com.example.myapplication.dataStorage.room.properties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "properties")
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true)
    val propertyId: Long,
    var address: String,
    val city: String,
    val userEmail: String
)
