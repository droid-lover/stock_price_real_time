package com.vs.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vs.models.Datum
import com.vs.models.StockDao

@Database(entities = arrayOf(Datum::class),version = 1,exportSchema = false)
abstract class StockDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao

    companion object {
        // Singleton will prevent multiple instances of database opening at the
        @Volatile
        private var INSTANCE: StockDatabase? = null

        fun getDatabase(context: Context): StockDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context,
                        StockDatabase::class.java,
                        "stock_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}