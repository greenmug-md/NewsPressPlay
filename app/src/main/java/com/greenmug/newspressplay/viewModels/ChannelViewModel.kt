package com.greenmug.newspressplay.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.greenmug.newspressplay.models.Channels
import com.greenmug.newspressplay.models.News
import com.greenmug.newspressplay.utilities.Constants
import kotlinx.coroutines.launch

/*
ViewModel for Channels
 */
class ChannelViewModel  (): ViewModel() {
    var list = MutableLiveData<ArrayList<News>>()
    var channels = MutableLiveData<ArrayList<Channels>>()
    fun  getContent()   {
            viewModelScope.launch {
                var l = ArrayList<News>();
                val database: FirebaseFirestore = FirebaseFirestore.getInstance()
                database.collection(Constants.KEY_COLLECTION_VIDEOS)
                    .whereEqualTo(Constants.KEY_CHANNEL, Constants.CHANNEL_0)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful() && task.getResult() != null && task?.getResult()
                                ?.getDocuments() != null && task!!.getResult()!!
                                .getDocuments()!!.size > 0
                        ) {
                            for (m in task?.getResult()!!.getDocuments()) {
                                var content_id = m.getString("content_id")
                                var name = m.getString("name")
                                var url = m.getString("url")
                                var image = m.getString("image")
                                var content = m.getString("content")
                                var s = News(name = name!!, image = image!!, url = url!!, content_id = content_id!!,content = content!!);
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

    fun  getChannels()   {
        viewModelScope.launch {
            var ch = ArrayList<Channels>();
            val database: FirebaseFirestore = FirebaseFirestore.getInstance()
            database.collection(Constants.KEY_COLLECTION_CHANNELS)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful() && task.getResult() != null && task?.getResult()
                            ?.getDocuments() != null && task!!.getResult()!!
                            .getDocuments()!!.size > 0
                    ) {
                        for (m in task?.getResult()!!.getDocuments()) {
                            var content = m.getString("content")
                            var url = m.getString("url")
                            var name = m.getString("name")
                            var channel = m.getString("channel")
                            var s = Channels(name = name!!,url = url!!,content = content!!,channel = channel!!);
                            ch.add(s);
                        }
                        channels.postValue(ch)
                    } else {

                    }
                }
                .addOnFailureListener {
                }

        }

    }
}