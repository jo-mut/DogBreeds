package com.challenge.dogbreed.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.challenge.dogbreed.models.Image

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: Image)
    @Update
    suspend fun updateImage(image: Image)
    @Delete
    suspend fun deleteImage(image: Image)

//    @Query("SELECT * FROM images")
//    suspend fun getImageByBreedIdImage(imageId: Int)

    @Query("SELECT * FROM images")
    fun getImages(): LiveData<List<Image>>
}