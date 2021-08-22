package com.greenmug.newspressplay.utilities

import android.content.Context
import android.content.SharedPreferences


class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences

    fun putBoolean(key: String?, value: Boolean?) {
        sharedPreferences.edit().putBoolean(key, value!!).apply()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun putString(key: String?, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, null)
    }

    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    init {
        sharedPreferences =
            context.getSharedPreferences(Constants.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
}