package com.martin.sofu.storage

interface AppSharedPreferences {
    fun getBookmarkIds(): ArrayList<Long>
    fun setBookmarkIds(bookmarkedIds: ArrayList<Long>)
}