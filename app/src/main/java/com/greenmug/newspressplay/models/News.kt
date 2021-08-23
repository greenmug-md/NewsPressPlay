package com.greenmug.newspressplay.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Data class for News
 */
@Entity(tableName = "tvNews")
data class News(
    @SerializedName("content_id") var content_id: String,
    @SerializedName("name") var name: String,
    @SerializedName("image") var image: String,
    @SerializedName("url") var url: String,
    @SerializedName("content") var content: String
): Serializable