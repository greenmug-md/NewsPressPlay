package com.greenmug.newspressplay.dao

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.room.*
import com.greenmug.newspressplay.models.Favourites
import io.reactivex.Completable
import io.reactivex.Flowable

/*
    Favourite Database Data Access Object
 */
@Dao
interface FavouriteShowDao {

    @Query("SELECT * from favourites")
     fun getWatchList():  LiveData<List<Favourites>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToWatchList(favourites: Favourites): Completable

    @Delete
    fun removeFromWatchList(favourites: Favourites): Completable

    @VisibleForTesting
    @Query("SELECT * FROM favourites")
    fun selectAll(): Flowable<List<Favourites>>
}