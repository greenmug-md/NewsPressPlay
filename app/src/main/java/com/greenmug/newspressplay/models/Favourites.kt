package com.greenmug.newspressplay.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Data class for Favourites
 */
@Entity(tableName = "favourites", primaryKeys = ["content_id","type"])
data class Favourites(
    @SerializedName("name") var name: String,
    @SerializedName("image") var image: String,
    @SerializedName("url") var url: String,
    @SerializedName("content_id") var content_id: String,
    @SerializedName("type") var type: String,
    @SerializedName("content") var content: String
): Serializable {
}