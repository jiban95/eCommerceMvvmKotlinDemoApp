package com.example.mykotlindemo.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mykotlindemo.Database.DAO.CartDataDao
import com.example.mykotlindemo.Database.Entity.CartTable

@Database(entities = [CartTable::class], version = 1, exportSchema = false)
abstract class VajroDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDataDao

    companion object {
        private var instance: VajroDatabase? = null
        private val roomCallback: Callback = object : Callback() {
        }

        @Synchronized
        fun getInstance(context: Context): VajroDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    VajroDatabase::class.java, "vajro_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }
    }
}