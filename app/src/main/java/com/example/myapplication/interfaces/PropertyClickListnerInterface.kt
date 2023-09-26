package com.example.myapplication.interfaces

import com.example.myapplication.models.PropertyWithDetails

interface PropertyClickListnerInterface {
    fun onPropertyClick(position: PropertyWithDetails)
    fun onPropertyDelete(position: PropertyWithDetails, currentposition: Int)
}