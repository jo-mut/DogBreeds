package com.challenge.dogbreed.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.dogbreed.Constants
import com.challenge.dogbreed.R
import com.challenge.dogbreed.adapters.BreedAdapter
import com.challenge.dogbreed.adapters.DogAdapter
import com.challenge.dogbreed.databinding.ActivityBreedsGridBinding
import com.challenge.dogbreed.interfaces.ApiService
import com.challenge.dogbreed.models.Breed
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BreedsGridActivity : AppCompatActivity() {
    lateinit var dogAdapter: DogAdapter;
    private lateinit var binding: ActivityBreedsGridBinding
    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    private val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    var mResponse: MutableList<Breed>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedsGridBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        getBreeds()
    }

    private fun getBreeds(): MutableList<Breed>? {
        okHttpClient.readTimeout(12, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(12, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(12, TimeUnit.SECONDS)

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(httpLoggingInterceptor)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()

        val request = retrofit.create(ApiService::class.java)
        val call = request.getBreeds(Constants.ACCESS_KEY, 102)
        call.enqueue(object : Callback<MutableList<Breed>> {
            override fun onFailure(call: Call<MutableList<Breed>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<MutableList<Breed>>,
                response: Response<MutableList<Breed>>
            ) {
                if (response.isSuccessful) {

                    mResponse = response.body()
                    if (mResponse != null) {
                        updateRecyclerView(mResponse)
                    }
                }
            }
        })

        return mResponse
    }

    fun updateRecyclerView(breeds: MutableList<Breed>?) {
        dogAdapter = DogAdapter(this@BreedsGridActivity, breeds!!)
        val layoutManager = GridLayoutManager(this@BreedsGridActivity, 3)
        binding.breedsRecyclerView.adapter = dogAdapter;
        binding.breedsRecyclerView.layoutManager = layoutManager;
        binding.breedsRecyclerView.setHasFixedSize(false)

    }
}