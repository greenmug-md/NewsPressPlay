package com.greenmug.newspressplay.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.greenmug.newspressplay.database.FavouriteShowDatabase
import com.greenmug.newspressplay.models.Favourites
import com.greenmug.newspressplay.models.News
import com.greenmug.newspressplay.utilities.Constants
import kotlinx.coroutines.launch


class TrendViewModel(application: Application) : AndroidViewModel(application) {
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
                            var s = News(name = name!!, image = image!!, url = url!!,content_id = content_id!!);
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
                            var s = News(name = name!!, image = image!!, url = url!!,content_id = content_id!!);
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