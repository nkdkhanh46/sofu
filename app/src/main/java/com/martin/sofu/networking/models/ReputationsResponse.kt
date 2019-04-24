package com.martin.sofu.networking.models

import com.google.gson.annotations.SerializedName
import com.martin.sofu.model.Reputation
import com.martin.sofu.model.User

class ReputationsResponse(val items: ArrayList<Reputation>, @SerializedName("has_more") val hasMore: Boolean)