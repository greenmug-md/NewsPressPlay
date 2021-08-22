package com.greenmug.newspressplay.listeners
import com.greenmug.newspressplay.models.Favourites


interface SaveLaterListener {

    fun bookMark(news: Favourites)

    fun favourite(news: Favourites)

}