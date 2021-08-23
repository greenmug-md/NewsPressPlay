package com.greenmug.newspressplay.network

import com.greenmug.newspressplay.models.EdgeNetCloud
import com.greenmug.newspressplay.models.EdgeNetUrl
import com.greenmug.newspressplay.utilities.AESEncyption
import com.greenmug.newspressplay.utilities.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/*
Retrofit ApiEdgeNetService Service
 */
interface ApiEdgeNetService {

    @GET("api/v1/stream-tech/content/get-all")
    suspend fun getAllContent(
        @Query("partner_name") partner_name:  String = AESEncyption.decrypt(Constants.PARTNER_ID)!!,
        @Header("api_key") api_key: String = AESEncyption.decrypt(Constants.API_KEY)!!
    ): Response<EdgeNetCloud>

    @GET("api/v1/stream-tech/content/get-url/{content_id}")
    suspend fun getContentId(
        @Path("content_id") content_id: String = "bcz",
        @Query("partner_name") partner_name:  String = AESEncyption.decrypt(Constants.PARTNER_ID)!!,
        @Header("api_key") api_key: String = AESEncyption.decrypt(Constants.API_KEY)!!
    ): Response<EdgeNetUrl>

}