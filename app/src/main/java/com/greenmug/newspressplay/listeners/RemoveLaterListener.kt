package com.greenmug.newspressplay.listeners

import com.greenmug.newspressplay.models.Favourites

/*
Interface to Remove Listener
 */
interface RemoveLaterListener {

    fun remove(news: Favourites, type: String)

}