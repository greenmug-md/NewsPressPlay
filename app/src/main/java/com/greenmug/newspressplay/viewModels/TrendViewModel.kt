package com.greenmug.newspressplay.viewModels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.greenmug.newspressplay.database.FavouriteShowDatabase
import com.greenmug.newspressplay.fragments.TrendFragment
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.models.News
import com.greenmug.newspressplay.player.PlayerActivity
import com.greenmug.newspressplay.repositories.EdgeNetRepository
import com.greenmug.newspressplay.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrendViewModel@Inject constructor(application: Application,
                      val edgeNetRepository: EdgeNetRepository
) : AndroidViewModel(application) {
     var favouriteShowDatabase: FavouriteShowDatabase?=null

    init {
        favouriteShowDatabase = FavouriteShowDatabase.getTvFacouritesDatabase(application)
    }
    var list = MutableLiveData<ArrayList<News>>()
    var database = MutableLiveData<List<Favourites>>()
    fun  getContent()   {
        viewModelScope.launch {
            var l = ArrayList<News>();
            val database: FirebaseFirestore = FirebaseFirestore.getInstance()
            database.collection(Constants.KEY_COLLECTION_VIDEOS)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful() && task.getResult() != null && task?.getResult()
                            ?.getDocuments() != null && task!!.getResult()!!
                            .getDocuments()!!.size > 0
                    ) {
                        for (m in task?.getResult()!!.getDocuments()) {

                            var name = m.getString("name")
                            var url = m.getString("url")
                            var image = m.getString("image")
                            var content_id  =  m.getString("content_id")
                            var content = m.getString("content")
                            var s = News(name = name!!, image = image!!, url = url!!,content_id = content_id!!, content = content!!);
                            l.add(s);
                        }
                        list.postValue(l)
                    } else {
                    }
                }
                .addOnFailureListener {
                }

        }

    }

    fun  getContent(channel:String)   {
        viewModelScope.launch {
            var l = ArrayList<News>();
            val database: FirebaseFirestore = FirebaseFirestore.getInstance()
            database.collection(Constants.KEY_COLLECTION_VIDEOS)
                .whereEqualTo("channel", channel)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful() && task.getResult() != null && task?.getResult()
                            ?.getDocuments() != null && task!!.getResult()!!
                            .getDocuments()!!.size > 0
                    ) {
                        for (m in task?.getResult()!!.getDocuments()) {

                            var name = m.getString("name")
                            var url = m.getString("url")
                            var image = m.getString("image")
                            var content_id  =  m.getString("content_id")
                            var content = m.getString("content")
                            var s = News(name = name!!, image = image!!, url = url!!,content_id = content_id!!,content = content!!);
                            l.add(s);
                        }
                        list.postValue(l)
                    } else {

                    }
                }
                .addOnFailureListener {
                }
        }
    }


}