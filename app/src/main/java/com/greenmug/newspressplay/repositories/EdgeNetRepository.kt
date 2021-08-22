package com.greenmug.newspressplay.repositories

import com.greenmug.newspressplay.models.EdgeNetCloud
import com.greenmug.newspressplay.models.EdgeNetCloudItem
import com.greenmug.newspressplay.models.EdgeNetUrl

import retrofit2.Response


interface EdgeNetRepository {
    suspend fun getAllContent(): Response<EdgeNetCloud>

    suspend fun getAllContentId(id: String): Response<EdgeNetUrl>
}