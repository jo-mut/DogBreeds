package com.challenge.dogbreed.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.dogbreed.activities.DogsActivity
import com.challenge.dogbreed.databinding.DogBreedBinding
import com.challenge.dogbreed.models.Breed

class BreedAdapter(
    private val context: Context,
    private val breedSerializers: List<Breed>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class BreedViewHolder(val binding: DogBreedBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DogBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var breed: Breed = breedSerializers[position]
        val breedViewHolder = holder as BreedViewHolder
        breedViewHolder.binding.breedTextView.text = breed.name
        breedViewHolder.binding.lifeSpanTextView.text = breed.life_span
        breedViewHolder.binding.bredForTextView.text = breed.bred_for
        breedViewHolder.binding.originTextView.text = breed.origin

//        Glide.with(context)
//            .asBitmap()
//            .load(breed.)
//            .into(breedViewHolder.binding.breedImageView)

        breedViewHolder.binding.root.setOnClickListener {
            var intent: Intent = Intent(context, DogsActivity::class.java)
            intent.putExtra("breed_id", breed.id.toString())
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return breedSerializers.size
    }
}