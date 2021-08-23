package com.greenmug.newspressplay.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.greenmug.newspressplay.network.ApiClient
import com.greenmug.newspressplay.network.ApiEdgeNetService
import com.greenmug.newspressplay.repositories.EdgeNetRepositoryImpl
import com.greenmug.newspressplay.utilities.Constants
import com.greenmug.newspressplay.viewModels.TrendViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/*
Tests for Trend View Model
 */
class  TrendViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    var apiClient = ApiClient.getRetrofitClient(Constants.EDGENET_CLOUD);
    var api = apiClient.create(ApiEdgeNetService::class.java)
    var trendViewModel = TrendViewModel(edgeNetRepository = EdgeNetRepositoryImpl(api), application = ApplicationProvider.getApplicationContext());

    @Test
    fun getChannel(){
        runBlocking {
            trendViewModel?.getContent()
            delay(2000)
            Assert.assertTrue(trendViewModel?.list?.value?.isNotEmpty()!!)
        }
    }


    @Test
    fun getContent(){
        runBlocking {
            trendViewModel?.getContent("1")
            delay(2000)
            Assert.assertTrue(trendViewModel?.list?.value?.isNotEmpty()!!)
        }
    }
}