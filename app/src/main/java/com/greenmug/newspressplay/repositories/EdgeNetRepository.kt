package com.greenmug.newspressplay.repositories

import com.greenmug.newspressplay.models.EdgeNetCloud
import com.greenmug.newspressplay.models.EdgeNetUrl
import retrofit2.Response

/*
Interface EdgeNetRepository
 */
interface EdgeNetRepository {
    suspend fun getAllContent(): Response<EdgeNetCloud>

    suspend fun getAllContentId(id: String): Response<EdgeNetUrl>
}