package com.challenge.dogbreed.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "measurements",
    foreignKeys = [androidx.room.ForeignKey(
        entity = Breed::class,
        parentColumns = ["id"],
        childColumns = ["breed_id"],
        onDelete = androidx.room.ForeignKey.CASCADE
    )],
    indices = [Index("breed_id")]
)
data class Measurement(
    @PrimaryKey(autoGenerate = false)
    var id: Int?,
    @ColumnInfo(name = "breed_id")
    var breed_id: String,
    @ColumnInfo(name = "type")
    var type: String,
    @ColumnInfo(name = "imperial")
    var imperial: String,
    @ColumnInfo(name = "metric")
    var metric: String
)
