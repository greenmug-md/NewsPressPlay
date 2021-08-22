package com.greenmug.newspressplay.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
        private var retrofit: Retrofit? = null
        fun getRetrofitClient(baseUrl: String): Retrofit {
                return  Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
        }
}