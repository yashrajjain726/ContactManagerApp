package com.example.contactmanagerapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDAO: UserDAO

    // SINGLETON
    companion object{
        @Volatile
        private var INSTANCE: UserDatabase ?= null
            fun getInstance(context:Context):UserDatabase {
                synchronized(this){
                    var instance = INSTANCE
                    if(instance==null){
                        instance = Room.databaseBuilder(context.applicationContext,UserDatabase::class.java,"users_db").build()

                    }
                    INSTANCE = instance
                    return instance;
                }
            }
    }
}