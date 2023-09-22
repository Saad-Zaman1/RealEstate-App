package com.example.myapplication.dataStorage.room.propertydetails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "propertyDetails")
class PropertyDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    val propertyDetailsId: Long,
    val propertyId: Long,
    val propertyName: String,
    val price: String,
    var rooms: String,
    val kitchen: String,
    val floors: String,
    val bathrooms: String,
    val furnished: String,
    val sale: String
)