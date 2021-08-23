package com.greenmug.newspressplay.viewModels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.greenmug.newspressplay.database.FavouriteShowDatabase
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.repositories.EdgeNetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchLaterViewModel@Inject constructor(application: Application,
     val edgeNetRepository: EdgeNetRepository
) : AndroidViewModel(application) {
    var edgeNetUrl = MutableLiveData<String>()

    var favouriteShowDatabase: FavouriteShowDatabase?=null
    init {
        favouriteShowDatabase = FavouriteShowDatabase.getTvFacouritesDatabase(application)
    }

    fun  startPlayer(url:String, content_id : String) {
        try {
            viewModelScope.launch {
                var edgeNet = edgeNetRepository?.getAllContentId(content_id)
                if (edgeNet != null && edgeNet?.body() != null && edgeNet?.isSuccessful && edgeNet?.body()?.url != null) {
                    edgeNetUrl.postValue(edgeNet?.body()?.url!!)
                }else {
                    edgeNetUrl.postValue(url);
                }
            }
        }catch (e : Exception) {
        }
    }

    fun remove(favourites: Favourites) {
        favouriteShowDatabase?.tvFacouritesDao()?.removeFromWatchList(favourites)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
            }
    }
}