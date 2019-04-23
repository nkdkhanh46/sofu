package com.martin.sofu.features.home

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

    var bookmarkedIds: ArrayList<Long> = sharedPreferences.getBookmarkIds()

    var currentPage = 1

    fun loadUsers(loadMore: Boolean = false) {
        if (loadMore) {
            currentPage += 1
        } else {
            currentPage = 1
        }
        userRepository.getUsers(currentPage, object : RepositoryCallback<ArrayList<User>> {
            override fun onSuccess(result: ArrayList<User>?) {
                refreshCompleted.value = true
            }
            override fun onFailed(error: String?) {
                refreshCompleted.value = true
            }
        })
    }

    fun updateBookmarksList(bookmarkedIds: ArrayList<Long>) {
        this.bookmarkedIds = bookmarkedIds
        sharedPreferences.setBookmarkIds(bookmarkedIds)
    }
}