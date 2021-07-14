package com.challenge.dogbreed.models

import com.google.gson.annotations.SerializedName


data class BreedSerializer(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("temperament") val temperament: String,
    @SerializedName("life_span") val life_span: String,
    @SerializedName("bred_for") val bred_for: String,
    @SerializedName("origin") val origin: String,
    @SerializedName("weight") val weight: MeasurementSerializer,
    @SerializedName("breed_group") val breed_group: String,
    @SerializedName("reference_image_id") val reference_image_id: String,
    @SerializedName("height") val height: MeasurementSerializer,
    @SerializedName("image") val image: ImageSerializer
)

data class MeasurementSerializer(
    @SerializedName("imperial") val imperial: String,
    @SerializedName("metric") val metric: String
)

data class ImageSerializer(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int
)

data class DogSerializer(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("temperament") val temperament: String,
    @SerializedName("life_span") val life_span: String,
    @SerializedName("bred_for") val bred_for: String,
    @SerializedName("origin") val origin: String,
    @SerializedName("weight") val weight: MeasurementSerializer,
    @SerializedName("breed_group") val breed_group: String,
    @SerializedName("reference_image_id") val reference_image_id: String,
    @SerializedName("height") val height: MeasurementSerializer,
)

data class DogBreedSerializer(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("breeds") val breeds: MutableList<DogSerializer>,
)






