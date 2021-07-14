package com.challenge.dogbreed.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.dogbreed.databinding.ItemBreedBinding
import com.challenge.dogbreed.models.DogBreedSerializer

class DogAdapter(
    private val context: Context,
    private val dogSerializers: MutableList<DogBreedSerializer>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class DogsViewHolder(val binding: ItemBreedBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dogSerializer: DogBreedSerializer = dogSerializers[position]
        val dogViewHolder = holder as DogsViewHolder
        Glide.with(context)
            .asBitmap()
            .load(dogSerializer.url)
            .into(dogViewHolder.binding.dogImageView)

    }

    override fun getItemCount(): Int {
        return dogSerializers.size
    }
}