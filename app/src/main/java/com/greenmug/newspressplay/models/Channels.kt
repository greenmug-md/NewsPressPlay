package com.greenmug.newspressplay.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable
/*
Data class for Channels
 */
@Entity(tableName = "Channels")
data class Channels(
    @SerializedName("name") var name: String,
    @SerializedName("content") var content: String,
    @SerializedName("url") var url: String,
    @SerializedName("channel") var channel: String,
): Serializable