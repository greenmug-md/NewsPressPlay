package com.greenmug.newspressplay.repositories

import com.greenmug.newspressplay.models.EdgeNetCloud
import com.greenmug.newspressplay.models.EdgeNetUrl
import com.greenmug.newspressplay.network.ApiEdgeNetService
import retrofit2.Response

/*
Implementation for EdgeNetRepository
 */
class EdgeNetRepositoryImpl(private val apiEdgeNet: ApiEdgeNetService) :  EdgeNetRepository  {

    override suspend fun getAllContent(): Response<EdgeNetCloud> {
            val response = apiEdgeNet.getAllContent()
            return response;

    }

    override suspend fun getAllContentId(id: String): Response<EdgeNetUrl> {
        val response = apiEdgeNet.getContentId(id)
        return response;
    }

}