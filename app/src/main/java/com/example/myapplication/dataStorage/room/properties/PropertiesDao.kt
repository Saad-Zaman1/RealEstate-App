package com.example.myapplication.dataStorage.room.properties

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
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
    suspend fun updateProperty(property: PropertyEntity)

    @Delete
    suspend fun deleteProperty(property: PropertyEntity)

    @Query("select * from properties p join propertyDetails pd On p.propertyId = pd.propertyId ")
    fun getAllProperties(): LiveData<List<PropertyWithDetails>>

    @Query("SELECT * FROM properties p JOIN propertyDetails pd ON p.propertyId = pd.propertyId WHERE p.userEmail = :userEmail")
    fun getCurrentUserProperties(userEmail: String): LiveData<List<PropertyWithDetails>>

    @Query(
        "SELECT * FROM properties p JOIN propertyDetails pd ON p.propertyId = pd.propertyId WHERE " +
                "pd.furnished = :furnished AND pd.sale = :sale AND pd.size = :size AND p.city = :city"
    )
    fun getFilteredProperties(
        furnished: String,
        sale: String,
        size: String,
        city: String
    ): LiveData<List<PropertyWithDetails>>

}