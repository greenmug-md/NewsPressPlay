package com.greenmug.newspressplay.network

import com.greenmug.newspressplay.models.EdgeNetCloud
import com.greenmug.newspressplay.models.EdgeNetCloudItem
import com.greenmug.newspressplay.models.EdgeNetUrl
import com.greenmug.newspressplay.utilities.Constants
import retrofit2.http.*
import retrofit2.Response

interface ApiEdgeNetService {

    @GET("api/v1/stream-tech/content/get-all")
    suspend fun getAllContent(
        @Query("partner_name") partner_name: String = Constants.PARTNER_ID,
        @Header("api_key") api_key: String = Constants.API_KEY
    ): Response<EdgeNetCloud>

    @GET("api/v1/stream-tech/content/get-url/{content_id}")
    suspend fun getContentId(
        @Path("content_id") content_id: String = "bcz",
        @Query("partner_name") partner_name: String = Constants.PARTNER_ID,
        @Header("api_key") api_key: String = Constants.API_KEY
    ): Response<EdgeNetUrl>

}