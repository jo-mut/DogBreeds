package com.challenge.dogbreed.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.dogbreed.Constants
import com.challenge.dogbreed.adapters.BreedAdapter
import com.challenge.dogbreed.databinding.ActivityMainBinding
import com.challenge.dogbreed.interfaces.ApiService
import com.challenge.dogbreed.models.Breed
import com.challenge.dogbreed.models.Dog
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var breedAdapter: BreedAdapter;
    private lateinit var binding: ActivityMainBinding
    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    private val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    var mResponse: MutableList<Breed>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        getBreeds()

    }

    /**
     *Get a list of dog breeds from the api**/
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
                Log.d("failure", t.toString())
            }

            override fun onResponse(
                call: Call<MutableList<Breed>>,
                response: Response<MutableList<Breed>>
            ) {
                if (response.isSuccessful) {
                    Log.d("success", mResponse.toString())
                    mResponse = response.body()
                    if (mResponse != null) {
                        updateRecyclerView(mResponse)
                    }
                }
            }
        })

        return mResponse
    }

    /**
     * update the recycler view with serialized api data**/
    fun updateRecyclerView(breeds: MutableList<Breed>?) {
        breedAdapter = BreedAdapter(this@MainActivity, breeds!!)
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.breedsRecyclerView.adapter = breedAdapter;
        binding.breedsRecyclerView.layoutManager = layoutManager;
        binding.breedsRecyclerView.setHasFixedSize(false)

    }
}