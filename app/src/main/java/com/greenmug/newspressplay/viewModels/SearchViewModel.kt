package com.greenmug.newspressplay.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.greenmug.newspressplay.database.FavouriteShowDatabase
import com.greenmug.newspressplay.models.News
import com.greenmug.newspressplay.repositories.EdgeNetRepository
import com.greenmug.newspressplay.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
ViewModel for Search View Model
 */
@HiltViewModel
class SearchViewModel@Inject constructor(application: Application,  val edgeNetRepository: EdgeNetRepository) : AndroidViewModel(application) {
    var favouriteShowDatabase: FavouriteShowDatabase?=null

    var edgeNetUrl = MutableLiveData<String>()
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