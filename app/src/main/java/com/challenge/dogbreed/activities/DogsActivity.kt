package com.challenge.dogbreed.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.dogbreed.Constants
import com.challenge.dogbreed.adapters.DogAdapter
import com.challenge.dogbreed.databinding.ActivityDogsBinding
import com.challenge.dogbreed.interfaces.ApiService
import com.challenge.dogbreed.models.Dog
import com.challenge.dogbreed.models.DogBreed
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DogsActivity : AppCompatActivity() {
    lateinit var dogsAdapter: DogAdapter;
    private lateinit var binding: ActivityDogsBinding
    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()

    private val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    var mResponse: MutableList<DogBreed>? = null
    private lateinit var breed_id: String
    val limit: Int = 102;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDogsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        if (intent.extras != null) {
            breed_id = intent.getStringExtra("breed_id")!!
            getDogsByBreeds(breed_id)
        }
    }

    private fun getDogsByBreeds(breed_id: String): MutableList<DogBreed>? {
//        okHttpClient.readTimeout(12, TimeUnit.SECONDS)
//        okHttpClient.connectTimeout(12, TimeUnit.SECONDS)
//        okHttpClient.writeTimeout(12, TimeUnit.SECONDS)
//


        okHttpClient.networkInterceptors().add(Interceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header("x-api-key", Constants.ACCESS_KEY)
            chain.proceed(requestBuilder.build())
        })

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(httpLoggingInterceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()

        val request = retrofit.create(ApiService::class.java)
        val call = request.getDogsByBreedName(breed_id, "RANDOM", "0", limit)
        call.enqueue(object : Callback<MutableList<DogBreed>> {
            override fun onFailure(call: Call<MutableList<DogBreed>>, t: Throwable) {
                Log.d("fail_of_dogs", mResponse.toString())

            }

            override fun onResponse(
                call: Call<MutableList<DogBreed>>,
                response: Response<MutableList<DogBreed>>
            ) {
                if (response.isSuccessful) {
                    mResponse = response.body()
                    if (mResponse != null) {
                                        Log.d("list_of_dogs", mResponse.toString())
                        updateRecyclerView(mResponse!!)
                    }
                }
            }
        })

        return mResponse
    }

    fun updateRecyclerView(dogBreeds: MutableList<DogBreed>) {
        dogsAdapter = DogAdapter(this@DogsActivity,  dogBreeds)
        val layoutManager = GridLayoutManager(this@DogsActivity, 3)
        binding.breedsRecyclerView.adapter = dogsAdapter;
        binding.breedsRecyclerView.layoutManager = layoutManager;
        binding.breedsRecyclerView.setHasFixedSize(false)
    }
}