package com.challenge.dogbreed.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.dogbreed.databinding.ItemBreedBinding
import com.challenge.dogbreed.models.Dog
import com.challenge.dogbreed.models.DogBreed

class DogAdapter(
    private val context: Context,
    private val dogs: MutableList<DogBreed>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class DogsViewHolder(val binding: ItemBreedBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dog: DogBreed = dogs[position]
        Log.d("breed_object", dog.toString())

        val dogViewHolder = holder as DogsViewHolder
        Glide.with(context)
            .asBitmap()
            .load(dog.url)
            .into(dogViewHolder.binding.dogImageView)

    }

    override fun getItemCount(): Int {
        return dogs.size
    }
}