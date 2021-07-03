package com.challenge.dogbreed.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.dogbreed.activities.BreedsGridActivity
import com.challenge.dogbreed.activities.DogsActivity
import com.challenge.dogbreed.databinding.DogBreedBinding
import com.challenge.dogbreed.models.Breed

class BreedAdapter(
    private val context: Context,
    private val breeds: MutableList<Breed>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class BreedViewHolder(val binding: DogBreedBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DogBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var dog: Breed = breeds[position]
        val breedViewHolder = holder as BreedViewHolder
        breedViewHolder.binding.breedTextView.text = dog.name
        breedViewHolder.binding.lifeSpanTextView.text = dog.life_span
        breedViewHolder.binding.bredForTextView.text = dog.bred_for
        breedViewHolder.binding.originTextView.text = dog.origin

        Glide.with(context)
            .asBitmap()
            .load(dog.image.url)
            .into(breedViewHolder.binding.breedImageView)

        breedViewHolder.binding.root.setOnClickListener {
            var intent: Intent = Intent(context, BreedsGridActivity::class.java)
            intent.putExtra("breed_id", dog.id.toString())
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return breeds.size
    }
}