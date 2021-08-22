package com.greenmug.newspressplay.di

import com.greenmug.newspressplay.network.ApiEdgeNetService
import com.greenmug.newspressplay.repositories.EdgeNetRepository
import com.greenmug.newspressplay.repositories.EdgeNetRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun getContentAll(edgeCloudApi: ApiEdgeNetService): EdgeNetRepository {
        return EdgeNetRepositoryImpl(edgeCloudApi)
    }


}