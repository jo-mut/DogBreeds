package com.challenge.dogbreed.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.dogbreed.Constants
import com.challenge.dogbreed.adapters.DogAdapter
import com.challenge.dogbreed.databinding.ActivityDogsBinding
import com.challenge.dogbreed.interfaces.ApiService
import com.challenge.dogbreed.models.DogBreedSerializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
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
    var mResponse: MutableList<DogBreedSerializer>? = null
    private lateinit var breed_id: String
    private val limit: Int = 100
    private val page: Int = 0

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

    /**
     *Get a list of images of the selected dog breeds from the api**/
    private fun getDogsByBreeds(breed_id: String): MutableList<DogBreedSerializer>? {

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
        val call = request.getDogsByBreedName(breed_id,  page, limit)
        call.enqueue(object : Callback<MutableList<DogBreedSerializer>> {
            override fun onFailure(call: Call<MutableList<DogBreedSerializer>>, t: Throwable) {
                Log.d("failure", t.toString())
            }

            override fun onResponse(
                call: Call<MutableList<DogBreedSerializer>>,
                response: Response<MutableList<DogBreedSerializer>>
            ) {
                if (response.isSuccessful) {
                    mResponse = response.body()
                    if (mResponse != null) {
                        Log.d("success", mResponse.toString())
                        updateRecyclerView(mResponse!!)
                    }
                }
            }
        })

        return mResponse
    }

    /**
     * update the recycler view with serialized api data**/
    fun updateRecyclerView(dogBreedSerializers: MutableList<DogBreedSerializer>) {
        dogsAdapter = DogAdapter(this@DogsActivity, dogBreedSerializers)
        val layoutManager = GridLayoutManager(this@DogsActivity, 3)
        binding.breedsRecyclerView.adapter = dogsAdapter;
        binding.breedsRecyclerView.layoutManager = layoutManager;
        binding.breedsRecyclerView.setHasFixedSize(false)
    }

}