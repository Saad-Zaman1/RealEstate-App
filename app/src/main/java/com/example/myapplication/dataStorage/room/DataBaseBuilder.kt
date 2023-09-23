package com.example.myapplication.dataStorage.room

import android.content.Context
import androidx.room.Room
import com.example.myapplication.global.GlobalVariables

object DataBaseBuilder {
    private var INSTANCE: DataBaseHelper? = null
    fun getInstance(context: Context): DataBaseHelper {
        if (INSTANCE == null) {
            synchronized(DataBaseHelper::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            DataBaseHelper::class.java,
            GlobalVariables.databaseName
        ).build()


}