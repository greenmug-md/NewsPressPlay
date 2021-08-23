package com.greenmug.newspressplay.network


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.greenmug.newspressplay.network.ApiClient.getRetrofitClient
import com.greenmug.newspressplay.utilities.Constants
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/*
Tests for Api EdgeNetService
 */
@RunWith(AndroidJUnit4::class)
class ApiEdgeNetServiceTest {

    var apiClient = getRetrofitClient(Constants.EDGENET_CLOUD);
    var api = apiClient.create(ApiEdgeNetService::class.java)

    @Test
    fun getEdgeNetAllVideos() {
        runBlocking {
            var s = api.getAllContent()
            Assert.assertNotNull(s?.body())
        }
    }

    @Test
    fun getEdgeNetExistingVideo() {
        runBlocking {
            var s = api.getContentId("bev")
            Assert.assertNotNull(s?.body())
        }
    }

    @Test
    fun getEdgeNetNoExistingVideo() {
        runBlocking {
            var s = api.getContentId("abc")
            Assert.assertNull(s?.body())
        }
    }
}