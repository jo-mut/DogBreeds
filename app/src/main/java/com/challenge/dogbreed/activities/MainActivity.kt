package com.challenge.dogbreed.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.dogbreed.Constants
import com.challenge.dogbreed.adapters.BreedAdapter
import com.challenge.dogbreed.databinding.ActivityMainBinding
import com.challenge.dogbreed.interfaces.ApiService
import com.challenge.dogbreed.models.Breed
import com.challenge.dogbreed.models.Dog
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var breedAdapter: BreedAdapter;
    private lateinit var binding: ActivityMainBinding
    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    private val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    var mResponse: MutableList<Breed>? = null
    private val page: Int = 0

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
        val call = request.getBreeds(page , 100)
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