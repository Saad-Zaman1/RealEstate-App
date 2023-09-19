package com.example.myapplication.dataStorage.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {


    //Room is competible with live data and automatically run this in background thread is return type is live data
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity): Long

    //    @Update
//    suspend fun updateUser(user: UserEntity)
//
//    @Delete
//    suspend fun deleteUser(user: UserEntity)
    @Query("select * from userAccounts")
    fun getAllUsers(): LiveData<MutableList<UserEntity>>
}
//Will execute on background thread => not on main thread.