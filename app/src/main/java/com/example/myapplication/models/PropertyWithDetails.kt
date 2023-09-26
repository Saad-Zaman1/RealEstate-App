package com.example.myapplication.models

import android.os.Parcelable
import java.io.Serializable

data class PropertyWithDetails(
    val propertyId: Long,
    val propertyDetailsId: Long,
    val userEmail: String,
    val address: String,
    val city: String,
    val size: String,
    val propertyName: String,
    val price: String,
    val rooms: String,
    val kitchen: String,
    val floors: String,
    val bathrooms: String,
    val furnished: String,
    val sale: String
) : Serializable
