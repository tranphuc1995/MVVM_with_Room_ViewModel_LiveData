package com.tranphuc.mvvm.repository.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.tranphuc.mvvm.repository.database.dao.PeopleDao
import com.tranphuc.mvvm.repository.database.entity.People


@Database(entities = [People::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao


    companion object {
        private var instance: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
                }
            }
            return instance
        }

        fun destroyDataBase() {
            instance = null
        }
    }
}