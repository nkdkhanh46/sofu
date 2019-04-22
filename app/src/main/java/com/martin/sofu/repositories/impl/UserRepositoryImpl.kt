package com.martin.sofu.repositories.impl

import com.martin.sofu.repositories.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun getUsers(): List<String> {
        return ArrayList<String>()
    }
}