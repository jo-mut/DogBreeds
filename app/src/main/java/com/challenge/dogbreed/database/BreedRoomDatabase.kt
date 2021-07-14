package com.challenge.dogbreed.database

import android.content.Context
import android.graphics.Movie
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.challenge.dogbreed.models.Breed
import com.challenge.dogbreed.models.Image
import com.challenge.dogbreed.models.Measurement

@Database(entities = [Breed::class, Image::class, Measurement::class], version = 1)
abstract class BreedRoomDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun imageDao(): ImageDao
    abstract fun measurementDao(): MeasurementDao

    companion object {
        @Volatile
        private var INSTANCE: BreedRoomDatabase? = null

        fun getDatabase(context: Context): BreedRoomDatabase {
            val dbInstance = INSTANCE
            if (dbInstance != null) {
                return dbInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BreedRoomDatabase::class.java, "breeds"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}