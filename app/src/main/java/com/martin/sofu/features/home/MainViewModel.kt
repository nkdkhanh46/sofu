package com.martin.sofu.features.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.martin.sofu.model.User
import com.martin.sofu.repositories.RepositoryCallback
import com.martin.sofu.repositories.UserRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val users: MutableLiveData<ArrayList<User>> = userRepository.users

    fun loadData() {
        userRepository.getUsers(object : RepositoryCallback<ArrayList<User>> {
            override fun onSuccess(result: ArrayList<User>?) {
            }

            override fun onFailed(error: String?) {}
        })
    }
}