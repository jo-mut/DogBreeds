package com.challenge.dogbreed.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.challenge.dogbreed.models.Breed

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreed(breed: Breed)
    @Update
    suspend fun updateBreed(breed: Breed)
    @Delete
    suspend fun deleteBreed(breed: Breed)

//    @Query("SELECT * FROM breeds")
//    suspend fun getBreedById(breedId: Int)

    @Query("SELECT * FROM breeds")
    fun getBreeds(): LiveData<List<Breed>>

}