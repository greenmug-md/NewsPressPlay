package com.greenmug.newspressplay.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.greenmug.newspressplay.database.FavouriteShowDatabase
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.repositories.EdgeNetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchLaterViewModel@Inject constructor(application: Application,
    private val edgeNetRepository: EdgeNetRepository
) : AndroidViewModel(application) {

    var favouriteShowDatabase: FavouriteShowDatabase?=null
    init {
        favouriteShowDatabase = FavouriteShowDatabase.getTvFacouritesDatabase(application)
    }
}