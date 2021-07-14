package com.challenge.dogbreed.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.dogbreed.BreedViewModel
import com.challenge.dogbreed.adapters.BreedAdapter
import com.challenge.dogbreed.database.BreedRoomDatabase
import com.challenge.dogbreed.database.BreedRoomDatabase.Companion.getDatabase
import com.challenge.dogbreed.databinding.ActivityMainBinding
import com.challenge.dogbreed.models.Breed
import com.challenge.dogbreed.repository.BreedRepository
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var breedAdapter: BreedAdapter;
    private lateinit var binding: ActivityMainBinding
    var mResponse: LiveData<List<Breed>>? = null
    var breedReposity: BreedRepository? = null
    var database: BreedRoomDatabase? = null
    var breedViewModel: BreedViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        database = getDatabase(this)
        breedViewModel = ViewModelProvider(this).get(BreedViewModel::class.java)
        breedViewModel?.getBreeds()?.observe(this) { breeds ->
            Log.d("response breeds", breeds.toString())
            updateRecyclerView(breeds)
        }


    }

    /**
     * update the recycler view with serialized api data**/
    private fun updateRecyclerView(breeds: List<Breed>?) {
        breedAdapter = BreedAdapter(this@MainActivity, breeds!!)
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.breedsRecyclerView.adapter = breedAdapter;
        binding.breedsRecyclerView.layoutManager = layoutManager;
        binding.breedsRecyclerView.setHasFixedSize(false)
    }
}