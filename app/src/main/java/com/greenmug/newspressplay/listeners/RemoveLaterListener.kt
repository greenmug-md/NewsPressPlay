package com.greenmug.newspressplay.listeners

import com.greenmug.newspressplay.models.Favourites

interface RemoveLaterListener {

    fun remove(news: Favourites, type: String)

}