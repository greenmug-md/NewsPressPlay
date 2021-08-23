package com.greenmug.newspressplay.viewmodels


import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.greenmug.newspressplay.network.ApiClient
import com.greenmug.newspressplay.network.ApiEdgeNetService
import com.greenmug.newspressplay.repositories.EdgeNetRepositoryImpl
import com.greenmug.newspressplay.utilities.Constants
import com.greenmug.newspressplay.viewModels.SearchViewModel
import com.greenmug.newspressplay.viewModels.TrendViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    var apiClient = ApiClient.getRetrofitClient(Constants.EDGENET_CLOUD);
    var api = apiClient.create(ApiEdgeNetService::class.java)
    var searchViewModel = SearchViewModel(edgeNetRepository = EdgeNetRepositoryImpl(api), application = ApplicationProvider.getApplicationContext());

    @Test
    fun getChannel(){
        runBlocking {
            searchViewModel?.getContent("Vin")
            delay(2000)
            Assert.assertTrue(searchViewModel?.list?.value?.isNotEmpty()!!)
        }
    }


}