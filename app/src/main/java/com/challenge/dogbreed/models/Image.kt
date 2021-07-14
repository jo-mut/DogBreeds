package com.challenge.dogbreed.models

import androidx.room.*

@Entity(
    tableName = "images",
    foreignKeys = [ForeignKey(
        entity = Breed::class,
        parentColumns = ["id"],
        childColumns = ["breed_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("breed_id")]
)
class Image() {
    @PrimaryKey(autoGenerate = true)
    var id: Int?  = null
    @ColumnInfo(name="image_id")
    var image_id: String?  = null
    @ColumnInfo(name="breed_id")
    var breed_id: String?  = null
    @ColumnInfo(name="url")
    var url: String?  = null
    @ColumnInfo(name="height")
    var height: Int?  = null
    @ColumnInfo(name="width")
    var width: Int?  = null
}
