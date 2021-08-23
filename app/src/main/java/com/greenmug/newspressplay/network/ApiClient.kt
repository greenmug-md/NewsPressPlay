package com.greenmug.newspressplay.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
Retrofit Api Client
 */
object ApiClient {
        private var retrofit: Retrofit? = null
        fun getRetrofitClient(baseUrl: String): Retrofit {
                return  Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
        }
}