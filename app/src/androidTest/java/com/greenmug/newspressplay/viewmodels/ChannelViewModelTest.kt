package com.greenmug.newspressplay.viewmodels

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.greenmug.newspressplay.viewModels.ChannelViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ChannelViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    var channelViewModel = ChannelViewModel();

    @Test
    fun getChannel(){
        runBlocking {
            channelViewModel?.getChannels()
            delay(2000)
             Assert.assertTrue(channelViewModel?.channels?.value?.isNotEmpty()!!)
        }
    }


    @Test
    fun getContent(){
        runBlocking {
            channelViewModel?.getContent()
            delay(2000)
            Assert.assertTrue(channelViewModel?.list?.value?.isNotEmpty()!!)
        }
    }
}