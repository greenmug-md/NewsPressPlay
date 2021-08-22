package com.greenmug.newspressplay.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.greenmug.newspressplay.models.EdgeNetCloud
import com.greenmug.newspressplay.network.Resource
import com.greenmug.newspressplay.repositories.EdgeNetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EdgeNetModule @Inject constructor(
    private val edgeNetRepository: EdgeNetRepository) : ViewModel()  {

    private val _edgenetAll = MutableLiveData<Resource<EdgeNetCloud?>>()
    val edgenetAll: LiveData<Resource<EdgeNetCloud?>> = _edgenetAll

    fun getAllContent()  {
     //    edgeNetRepository?.getAllContent();
    }
}