package com.challenge.dogbreed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.challenge.dogbreed.models.Breed
import com.challenge.dogbreed.repository.BreedRepository

class BreedViewModel(application: Application): AndroidViewModel(application) {
    val breedRepository: BreedRepository = BreedRepository(application)

    fun getBreeds(): LiveData<List<Breed>>? {
        return breedRepository.getBreeds()
    }
}