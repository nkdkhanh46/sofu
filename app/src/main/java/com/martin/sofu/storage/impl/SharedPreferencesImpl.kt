package com.martin.sofu.storage.impl

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.LongSparseArray
import androidx.core.util.valueIterator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.martin.sofu.model.User
import com.martin.sofu.storage.AppSharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesImpl @Inject constructor(private val context: Context): AppSharedPreferences {

    companion object {
        const val KEY_BOOKMARKS = "Bookmarks"
    }

    private fun getSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private fun getEditor(): SharedPreferences.Editor = getSharedPreferences().edit()

    private fun setString(key: String, value: String?) {
        val editor = getEditor()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getString(key: String): String {
        return getSharedPreferences().getString(key, "")?:""
    }

    override fun getBookmarks(): LongSparseArray<User> {
        val result = LongSparseArray<User>()

        val saved = getString(KEY_BOOKMARKS)
        if (saved.isEmpty())  return result

        val users = Gson().fromJson<ArrayList<User>>(saved, object : TypeToken<ArrayList<User>>() {}.type)
        for (user in users) {
            result.append(user.userId, user)
        }

        return result
    }

    override fun setBookmarks(bookmarks: LongSparseArray<User>) {
        val users = bookmarks.valueIterator()
        val data = ArrayList<User>()
        for (user in users) {
            data.add(user)
        }
        val text = Gson().toJson(data)
        setString(KEY_BOOKMARKS, text)
    }
}