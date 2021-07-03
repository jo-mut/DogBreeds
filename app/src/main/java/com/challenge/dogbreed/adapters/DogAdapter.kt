package com.challenge.dogbreed.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.dogbreed.databinding.ItemBreedBinding
import com.challenge.dogbreed.models.Breed

class DogAdapter(
    private val context: Context,
    private val breeds: MutableList<Breed>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class DogsViewHolder(val binding: ItemBreedBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val breed: Breed = breeds[position]
        Log.d("breed_object", breed.toString())

        val dogViewHolder = holder as DogsViewHolder
        Glide.with(context)
            .asBitmap()
            .load(breed.image.url)
            .into(dogViewHolder.binding.dogImageView)

    }

    override fun getItemCount(): Int {
        return breeds.size
    }
}