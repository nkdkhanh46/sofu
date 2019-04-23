package com.martin.sofu.model

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("badge_counts")
    val badgeCount = HashMap<String, Long>()

    @SerializedName("reputation")
    val reputation: Long = 0

    @SerializedName("user_id")
    val userId: Long = 0

    @SerializedName("last_access_date")
    val lastAccessDate: Long = 0

    @SerializedName("profile_image")
    val profileImage: String = ""

    @SerializedName("display_name")
    val name: String = ""

    @SerializedName("location")
    val location: String? = ""

    var isFooter: Boolean = false
}