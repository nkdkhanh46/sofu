package com.martin.sofu.features.home

import android.util.LongSparseArray
import androidx.core.util.putAll
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.martin.sofu.model.User
import com.martin.sofu.repositories.RepositoryCallback
import com.martin.sofu.repositories.UserRepository
import com.martin.sofu.storage.AppSharedPreferences
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository,
                                        private val sharedPreferences: AppSharedPreferences): ViewModel() {

    val users: MutableLiveData<ArrayList<User>> = userRepository.users
    var refreshCompleted = MutableLiveData<Boolean>()

    var bookmarks: LongSparseArray<User> = sharedPreferences.getBookmarks()

    var currentPage = 1
    var hasMore = true
    var showingAll = true

    init {
        loadUsers()
    }

    fun loadUsers(loadMore: Boolean = false) {
        if (loadMore) {
            currentPage += 1
        } else {
            currentPage = 1
            hasMore = true
        }
        userRepository.getUsers(currentPage, object : RepositoryCallback<Boolean> {
            override fun onSuccess(result: Boolean?) {
                refreshCompleted.value = true
                hasMore = result?: true
            }
            override fun onFailed(error: String?) {
                refreshCompleted.value = true
                hasMore = true
            }
        })
    }

    fun updateBookmarksList(bookmarks: LongSparseArray<User>) {
        this.bookmarks.clear()
        this.bookmarks.putAll(bookmarks)
    }

    fun updateFilterOption(showAll: Boolean) {
        this.showingAll = showAll
    }

    fun saveBookmarks() {
        sharedPreferences.setBookmarks(bookmarks)
    }

    fun setCurrentUser(user: User) {
        userRepository.updateCurrentUser(user)
    }
}