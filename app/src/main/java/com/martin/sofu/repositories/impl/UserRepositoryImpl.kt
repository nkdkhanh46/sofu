package com.martin.sofu.repositories.impl

import androidx.lifecycle.MutableLiveData
import com.martin.sofu.model.User
import com.martin.sofu.networking.RetrofitClient
import com.martin.sofu.networking.models.UsersResponse
import com.martin.sofu.repositories.RepositoryCallback
import com.martin.sofu.repositories.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val retrofitClient: RetrofitClient) : UserRepository {

    override val users: MutableLiveData<ArrayList<User>> = MutableLiveData()

    override fun getUsers(page: Int, callback: RepositoryCallback<Boolean>) {
        val call = retrofitClient.getService().getUsers(page)
        call.enqueue(object : Callback<UsersResponse> {
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                callback.onFailed()
            }

            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    if (page == 1) {
                        users.value = response.body()!!.items
                    } else {
                        val currentUsers = users.value
                        currentUsers?.addAll(response.body()!!.items)
                        users.value = currentUsers
                    }
                    callback.onSuccess(response.body()?.hasMore)
                    return
                }

                callback.onFailed()
            }
        })
    }
}