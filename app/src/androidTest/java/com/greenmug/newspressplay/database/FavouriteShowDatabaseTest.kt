package com.greenmug.newspressplay.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.greenmug.newspressplay.dao.FavouriteShowDao
import com.greenmug.newspressplay.models.Favourites
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
Android Test for Favourite Database
 */
@RunWith(AndroidJUnit4::class)
class FavouriteShowDatabaseTest : TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: FavouriteShowDatabase
    private lateinit var dbdao: FavouriteShowDao

    @Before
    public override fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(application,FavouriteShowDatabase::class.java).allowMainThreadQueries().build() //only for testing
        dbdao = db.tvFacouritesDao()
    }

    @Test
    fun writeAndReadFavourites() {
            val favourite = Favourites("Travel", "https:://image", "https:://url", "bev", "F" ,"Movie")
            dbdao.addToWatchList(favourite).blockingAwait()
            dbdao.selectAll().test().assertValue { list ->
                list.isNotEmpty()
            }
    }

    @Test
    fun writeAndReadBookmarks() {
        val channel = Favourites("Travel", "https:://image", "https:://url", "bev", "B", "News")
        dbdao.addToWatchList(channel).blockingAwait()
        dbdao.selectAll().test().assertValue { list ->
            list.isNotEmpty()
        }
    }

    @Test
    fun writeAndDeleteBookmarks() {
        val channel = Favourites("Travel", "https:://image", "https:://url", "bev", "B","News")
        dbdao.addToWatchList(channel).blockingAwait()
        dbdao.removeFromWatchList(channel).blockingAwait()
        dbdao.selectAll().test().assertValue { list ->
            list.isEmpty()
        }
    }

    @After
    fun closeDB() {
        db.clearAllTables()
        db.close()
    }
}