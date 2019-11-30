package com.martin.sofu.features.reputationhistory

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.martin.sofu.R
import com.martin.sofu.model.Reputation
import com.martin.sofu.model.User
import com.martin.sofu.repositories.RepositoryCallback
import com.martin.sofu.repositories.UserRepository

class ReputationViewModel constructor(
    private val context: Context,
    private val userRepository: UserRepository
): ViewModel() {

    val reputations: MutableLiveData<ArrayList<Reputation>> = userRepository.reputations
    var refreshCompleted = MutableLiveData<Boolean>()
    var profileImage = MutableLiveData<String>()

    val userName = ObservableField("")
    val totalReputation = ObservableField("")
    val location = ObservableField("")

    val user: User? = userRepository.currentUser
    var currentPage = 1
    var hasMore = true
    var userId: Long = 0
        set(value) {
            field = value
            loadReputations()
        }

    init {
        loadReputations()
        bindUser()
    }

    private fun bindUser() {
        if (user == null) return

        userName.set(user.name)

        val reputation = "${user.reputation} ${context.getString(R.string.reputation)}"
        totalReputation.set(reputation)

        val loc = if (user.location.isNullOrEmpty()) context.getString(R.string.unknown_location) else user.location
        location.set(loc)

        profileImage.value = user.profileImage
    }

    fun loadReputations(loadMore: Boolean = false) {
        if (userId == 0L) return

        if (loadMore) {
            currentPage += 1
        } else {
            currentPage = 1
            hasMore = true
        }

        userRepository.getUserReputations(userId, currentPage, object : RepositoryCallback<Boolean> {
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
}