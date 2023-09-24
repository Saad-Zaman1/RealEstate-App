package com.example.myapplication.dataStorage.room.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {

    //Room is competible with live data and automatically run this in background thread is return type is live data
    @Insert()
    suspend fun insertUser(user: UserEntity): Long

    @Update
    suspend fun updateUser(user: UserEntity)

    //
//    @Delete
//    suspend fun deleteUser(user: UserEntity)
    @Query("select * from userAccounts")
    fun getAllUsers(): LiveData<MutableList<UserEntity>>

    @Query("select * from userAccounts where email = :email1 AND password = :pass")
    suspend fun validateEmailAndPhone(email1: String, pass: String): UserEntity?

    @Query("select * from userAccounts where phone = :phonee")
    suspend fun validatePhone(phonee: String): UserEntity?

    @Query("select * from userAccounts where email = :emaii")
    suspend fun validateEmail(emaii: String): UserEntity?

    @Query("select * from userAccounts where password = :pass")
    suspend fun validatePassword(pass: String): UserEntity?

    @Query("select password from userAccounts where email = :emai")
    suspend fun findPassWithEmail(emai: String): String
}
//Will execute on background thread => not on main thread.