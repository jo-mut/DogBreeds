package com.challenge.dogbreed.interfaces

import com.challenge.dogbreed.models.BreedSerializer
import com.challenge.dogbreed.models.DogBreedSerializer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("breeds")
    fun getBreeds(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<MutableList<BreedSerializer>>

    @GET("images/search?")
    fun getDogsByBreedName(
        @Query("breed_id") breed_id: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<MutableList<DogBreedSerializer>>

}