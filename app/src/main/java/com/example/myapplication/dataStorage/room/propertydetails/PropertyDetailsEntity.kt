package com.example.myapplication.dataStorage.room.propertydetails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "propertyDetails")
class PropertyDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    val propertyDetailsId: Long,
    var rooms: Int,
    val kitchen: Int,
    val floors: Int,
    val furnished: Boolean,
    val sale: Boolean,
    val rent: Boolean
)