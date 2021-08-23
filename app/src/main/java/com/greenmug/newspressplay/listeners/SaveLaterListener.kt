package com.greenmug.newspressplay.listeners
import com.greenmug.newspressplay.models.Favourites

/*
Interface to Save Favourites and Bookmarks Listener
 */
interface SaveLaterListener {

    fun bookMark(news: Favourites)

    fun favourite(news: Favourites)

}