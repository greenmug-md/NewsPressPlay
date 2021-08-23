package com.greenmug.newspressplay.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.greenmug.newspressplay.database.FavouriteShowDatabase
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.repositories.EdgeNetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/*
ViewModel for Watch Later View Model
 */
@HiltViewModel
class WatchLaterViewModel@Inject constructor(application: Application,
     val edgeNetRepository: EdgeNetRepository
) : AndroidViewModel(application) {
    var edgeNetUrl = MutableLiveData<String>()

    var favouriteShowDatabase: FavouriteShowDatabase?=null
    init {
        favouriteShowDatabase = FavouriteShowDatabase.getTvFacouritesDatabase(application)
    }

    fun remove(favourites: Favourites) {
        favouriteShowDatabase?.tvFacouritesDao()?.removeFromWatchList(favourites)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
            }
    }
}