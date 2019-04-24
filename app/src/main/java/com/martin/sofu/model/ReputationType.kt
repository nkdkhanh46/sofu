package com.martin.sofu.model

import com.martin.sofu.R

enum class ReputationType(val apiValue: String, val displayValue: Int, val icon: Int, val color: Int) {
    UP_VOTE("post_upvoted", R.string.reputation_increased_by, R.drawable.ic_up, R.color.green),
    DOWN_VOTE("post_downvoted", R.string.reputation_decreased_by, R.drawable.ic_down, R.color.red);

    companion object {
        fun findType(apiValue: String): ReputationType {
            return when (apiValue) {
                DOWN_VOTE.apiValue -> DOWN_VOTE
                else -> UP_VOTE
            }
        }
    }
}