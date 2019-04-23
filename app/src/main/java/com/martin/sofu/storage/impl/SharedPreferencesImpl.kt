package com.martin.sofu.storage.impl

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.martin.sofu.storage.AppSharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesImpl @Inject constructor(private val context: Context): AppSharedPreferences {

    companion object {
        const val KEY_BOOKMARK_IDS = "BookmarkIds"
    }

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

    override fun getBookmarkIds(): ArrayList<Long> {
        val result = getString(KEY_BOOKMARK_IDS)
        if (result.isEmpty()) return ArrayList()

        val ids = ArrayList<Long>()
        ids.addAll(result.split(";").map { text -> text.toLong() })
        return ids
    }

    override fun setBookmarkIds(bookmarkedIds: ArrayList<Long>) {
        val result = bookmarkedIds.joinToString(";")
        setString(KEY_BOOKMARK_IDS, result)
    }
}