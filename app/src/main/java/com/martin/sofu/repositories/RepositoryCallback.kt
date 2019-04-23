package com.martin.sofu.repositories

interface RepositoryCallback<T> {
    fun onSuccess(result: T? = null)
    fun onFailed(error: String? = null)
}