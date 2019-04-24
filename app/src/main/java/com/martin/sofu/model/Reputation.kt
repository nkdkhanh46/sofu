package com.martin.sofu.model

import com.google.gson.annotations.SerializedName

class Reputation {
    @SerializedName("reputation_change")
    val reputationChange: Long = 0

    @SerializedName("user_id")
    val userId: Long = 0

    @SerializedName("post_id")
    val postId: Long = 0

    @SerializedName("creation_date")
    val creationDate: Long = 0

    @SerializedName("reputation_history_type")
    val historyType: String = ""

    var isFooter: Boolean = false
    val type: ReputationType
        get() = ReputationType.findType(historyType)
}