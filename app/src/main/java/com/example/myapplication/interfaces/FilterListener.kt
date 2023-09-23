package com.example.myapplication.interfaces

import com.example.myapplication.models.PropertyWithDetails

interface FilterListener {
    fun filterApplied(filteredData: List<PropertyWithDetails>)

}
