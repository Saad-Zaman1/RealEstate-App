package com.example.myapplication.dataStorage.room.properties

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.dataStorage.room.user.UserEntity
import com.example.myapplication.models.PropertyWithDetails

@Dao
interface PropertiesDao {

    @Insert()
    suspend fun insertProperty(property: PropertyEntity): Long

    @Update
    suspend fun updateProperty(user: UserEntity)

    @Query("select * from properties p join propertyDetails pd On p.propertyId = pd.propertyId ")
    fun getAllProperties(): LiveData<List<PropertyWithDetails>>

}