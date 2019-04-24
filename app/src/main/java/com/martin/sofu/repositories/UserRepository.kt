package com.martin.sofu.repositories

import androidx.lifecycle.MutableLiveData
import com.martin.sofu.model.Reputation
import com.martin.sofu.model.User

interface UserRepository {
    var currentUser: User?
    val users: MutableLiveData<ArrayList<User>>
    val reputations: MutableLiveData<ArrayList<Reputation>>

    fun getUsers(page: Int, callback: RepositoryCallback<Boolean>)

    fun getUserReputations(userId: Long, page: Int, callback: RepositoryCallback<Boolean>)
    fun updateCurrentUser(user: User)
}