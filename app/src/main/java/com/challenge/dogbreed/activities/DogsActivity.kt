package com.challenge.dogbreed.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.dogbreed.Constants
import com.challenge.dogbreed.adapters.DogAdapter
import com.challenge.dogbreed.databinding.ActivityDogsBinding
import com.challenge.dogbreed.interfaces.ApiService
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


class DogsActivity : AppCompatActivity() {
    lateinit var dogsAdapter: DogAdapter;
    private lateinit var binding: ActivityDogsBinding
    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()

    private val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    var mResponse: MutableList<DogBreed>? = null
    private lateinit var breed_id: String
    private val limit: Int = 10

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

//        // Define the interceptor, add authentication headers
//        val interceptor = Interceptor { chain ->
//            val requestBuilder: Request.Builder = chain.request().newBuilder()
//            requestBuilder.addHeader("Content-Type", "application/json")
//            requestBuilder.addHeader("x-api-key", "f9eab4d3-46c2-4208-aeb9-1ce02b8e2b94")
//            chain.proceed(requestBuilder.build())
//        }

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptor)

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
        dogsAdapter = DogAdapter(this@DogsActivity, dogBreeds)
        val layoutManager = GridLayoutManager(this@DogsActivity, 3)
        binding.breedsRecyclerView.adapter = dogsAdapter;
        binding.breedsRecyclerView.layoutManager = layoutManager;
        binding.breedsRecyclerView.setHasFixedSize(false)
    }


}