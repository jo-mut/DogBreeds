package com.challenge.dogbreed.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.challenge.dogbreed.models.Measurement
@Dao
interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurement(measurement: Measurement)
    @Update
    suspend fun updateMeasurement(measurement: Measurement)
    @Delete
    suspend fun deleteMeasurement(measurement: Measurement)

//    @Query("SELECT * FROM breeds")
//    suspend fun getBreedById(measurement: Breed)

    @Query("SELECT * FROM measurements")
    fun getMeasurements(): LiveData<List<Measurement>>
}