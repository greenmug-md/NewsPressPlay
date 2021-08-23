package com.greenmug.newspressplay.database

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.greenmug.newspressplay.dao.FavouriteShowDao
import com.greenmug.newspressplay.models.Favourites
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.MainThreadDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import java.util.Observer

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