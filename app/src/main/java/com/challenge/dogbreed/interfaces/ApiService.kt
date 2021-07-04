package com.challenge.dogbreed.interfaces

import com.challenge.dogbreed.Constants
import com.challenge.dogbreed.models.Breed
import com.challenge.dogbreed.models.DogBreed
import com.challenge.dogbreed.models.Dog
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("breeds")
    fun getBreeds(
        @Query("api_key") api_key: String,
        @Query("limit") limit: Int
    ): Call<MutableList<Breed>>


    @Headers("x-api-key: f9eab4d3-46c2-4208-aeb9-1ce02b8e2b94")
    @GET("images/search?")
    fun getDogsByBreedName(
        @Query("breed_id") breed_id: String,
        @Query("order") order: String,
        @Query("page") page: String,
        @Query("limit") limit: Int,
        @Query("x-api-key") api_key: String
    ): Call<MutableList<DogBreed>>

}