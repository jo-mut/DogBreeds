package com.challenge.dogbreed.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.challenge.dogbreed.Constants
import com.challenge.dogbreed.database.BreedDao
import com.challenge.dogbreed.database.BreedRoomDatabase
import com.challenge.dogbreed.database.ImageDao
import com.challenge.dogbreed.database.MeasurementDao
import com.challenge.dogbreed.interfaces.ApiService
import com.challenge.dogbreed.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BreedRepository(application: Application) {
    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    private val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    var mResponse: MutableList<BreedSerializer>? = null
    private val page: Int = 0
    private var mBreedDatabase: BreedRoomDatabase? = BreedRoomDatabase.getDatabase(application)

    /**
     *Get a list of dog breeds from the api**/
    fun fetchBreeds() {
        //interceptor, add api key and headers
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("x-api-key", "f9eab4d3-46c2-4208-aeb9-1ce02b8e2b94")
                .build()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }


        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()


        val request = retrofit.create(ApiService::class.java)
        val call = request.getBreeds(page, 100)
        call.enqueue(object : Callback<MutableList<BreedSerializer>> {
            override fun onFailure(call: Call<MutableList<BreedSerializer>>, t: Throwable) {
                Log.d("failure", t.toString())
            }

            override fun onResponse(
                call: Call<MutableList<BreedSerializer>>,
                response: Response<MutableList<BreedSerializer>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        mResponse = response.body();
                        mResponse?.forEach { it ->
                            GlobalScope.launch(Dispatchers.IO) {
                                insertBreed(it)
                            }
                        }
                    }
                }
            }
        })

    }


    // Breeds repository table database operations
    suspend fun insertBreed(serializer: BreedSerializer) {
        val b: Breed = Breed()
        b.id = serializer.id
        b.bred_for = serializer.bred_for
        b.breed_group = serializer.breed_group
        b.life_span = serializer.life_span
        b.name = serializer.name
        b.origin = serializer.origin
        b.reference_image_id = serializer.reference_image_id
        b.temperament = serializer.temperament

        mBreedDatabase?.breedDao()?.insertBreed(b)
        insertImage(serializer.image, b.id.toString())
    }

    suspend fun deleteBreeds(breed: Breed) {
        mBreedDatabase?.breedDao()?.deleteBreed(breed)

    }

    suspend fun updateBreedById(breed: Breed) {
        mBreedDatabase?.breedDao()?.updateBreed(breed)

    }

    fun getBreeds(): LiveData<List<Breed>>? {
        fetchBreeds()
        return mBreedDatabase?.breedDao()?.getBreeds()
    }

    // Images repository database operations
    suspend fun insertImage(serializer: ImageSerializer, breed_id: String) {
        var image: Image = Image()
        image.image_id = serializer.id
        image.breed_id = breed_id
        image.height = serializer.height
        image.width = serializer.width
        mBreedDatabase?.imageDao()?.insertImage(image)

    }


    // Measurements repository database operations
    suspend fun insertMeasurements(serializer: Measurement) {

    }

}