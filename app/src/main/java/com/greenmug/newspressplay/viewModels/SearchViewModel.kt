package com.greenmug.newspressplay.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.greenmug.newspressplay.database.FavouriteShowDatabase
import com.greenmug.newspressplay.listeners.SaveLaterListener
import com.greenmug.newspressplay.models.Channels
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.models.News
import com.greenmug.newspressplay.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


class SearchViewModel(application: Application) : AndroidViewModel(application) {
    var favouriteShowDatabase: FavouriteShowDatabase?=null


    init {
        favouriteShowDatabase = FavouriteShowDatabase.getTvFacouritesDatabase(application)
    }

    var list = MutableLiveData<ArrayList<News>>()
    fun  getContent(search : String)   {
        viewModelScope.launch {
            //First Get all Database Items:
            var l = ArrayList<News>();
            val database: FirebaseFirestore = FirebaseFirestore.getInstance()
            database.collection(Constants.KEY_COLLECTION_VIDEOS).whereGreaterThanOrEqualTo("name", search)
                .whereLessThan("name", search+"z")
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
                            var s = News(name = name!!, image = image!!, url = url!!,content_id = content_id!!);
                            l.add(s);
                        }
                        list.postValue(l)
                        Log.d("Malathi", "I am here ")
                    } else {

                    }
                }
                .addOnFailureListener {
                    Log.d("Malathi", "Genre = ")
                }

        }

    }




}