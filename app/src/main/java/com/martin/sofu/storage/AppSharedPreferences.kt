package com.martin.sofu.storage

import android.util.LongSparseArray
import com.martin.sofu.model.User

interface AppSharedPreferences {
    fun getBookmarks(): LongSparseArray<User>
    fun setBookmarks(bookmarks: LongSparseArray<User>)
}