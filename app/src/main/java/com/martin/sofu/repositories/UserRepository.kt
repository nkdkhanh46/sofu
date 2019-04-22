package com.martin.sofu.repositories

interface UserRepository {
    fun getUsers(): List<String>
}