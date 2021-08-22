package com.greenmug.newspressplay.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.greenmug.newspressplay.dao.FavouriteShowDao
import com.greenmug.newspressplay.models.Favourites

/*
    Favourite Database
 */
@Database(entities = [Favourites::class], version = 1, exportSchema = false)
abstract class FavouriteShowDatabase : RoomDatabase() {

    companion object {
        private var tvShowDatabase: FavouriteShowDatabase? = null

        fun getTvFacouritesDatabase(context: Context): FavouriteShowDatabase? {
            if (tvShowDatabase == null) {
                tvShowDatabase =
                    Room.databaseBuilder(context, FavouriteShowDatabase::class.java, "tv_favourites_db").build()
            }
            return tvShowDatabase
        }
    }

    abstract fun tvFacouritesDao(): FavouriteShowDao
}