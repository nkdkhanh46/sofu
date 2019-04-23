package com.martin.sofu.repositories

import androidx.lifecycle.MutableLiveData
import com.martin.sofu.model.User

interface UserRepository {

    val users: MutableLiveData<ArrayList<User>>

    fun getUsers(page: Int, callback: RepositoryCallback<ArrayList<User>>)
}