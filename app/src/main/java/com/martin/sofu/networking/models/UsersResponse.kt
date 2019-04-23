package com.martin.sofu.networking.models

import com.google.gson.annotations.SerializedName
import com.martin.sofu.model.User

class UsersResponse(val items: ArrayList<User>, @SerializedName("has_more") val hasMore: Boolean)