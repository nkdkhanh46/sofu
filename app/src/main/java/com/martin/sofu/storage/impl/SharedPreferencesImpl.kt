package com.martin.sofu.storage.impl

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.martin.sofu.storage.AppSharedPreferences

class SharedPreferencesImpl (private val context: Context): AppSharedPreferences {

    private fun getSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private fun getEditor(): SharedPreferences.Editor = getSharedPreferences().edit()

    private fun removeKey(key: String) {
        val editor = getEditor()
        editor.remove(key)
        editor.apply()
    }

    private fun setString(key: String, value: String?) {
        val editor = getEditor()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getString(key: String): String {
        return getSharedPreferences().getString(key, "")?:""
    }

    private fun setBoolean(key: String, value: Boolean) {
        val editor = getEditor()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String): Boolean {
        return getSharedPreferences().getBoolean(key, false)
    }

    private fun setInt(key: String, value: Int) {
        val editor = getEditor()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun getInt(key: String): Int {
        return getSharedPreferences().getInt(key, 0)
    }
}