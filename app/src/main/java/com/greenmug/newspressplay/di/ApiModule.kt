package com.greenmug.newspressplay.di

import com.greenmug.newspressplay.network.ApiClient
import com.greenmug.newspressplay.network.ApiEdgeNetService
import com.greenmug.newspressplay.utilities.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun getEdgeNetCloud(): ApiEdgeNetService =
        ApiClient.getRetrofitClient(Constants.EDGENET_CLOUD)
            .create(ApiEdgeNetService::class.java)
}