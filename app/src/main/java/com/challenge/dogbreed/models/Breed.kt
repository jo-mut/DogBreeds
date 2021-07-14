package com.challenge.dogbreed.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "breeds",
    indices = [Index("name")]
)
class Breed() {
    @PrimaryKey(autoGenerate = false)
    var id: Int? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "temperament")
    var temperament: String? = null

    @ColumnInfo(name = "life_span")
    var life_span: String? = null

    @ColumnInfo(name = "bred_for")
    var bred_for: String? = null

    @ColumnInfo(name = "origin")
    var origin: String? = null

    @ColumnInfo(name = "breed_group")
    var breed_group: String? = null

    @ColumnInfo(name = "reference_image_id")
    var reference_image_id: String? = null
}