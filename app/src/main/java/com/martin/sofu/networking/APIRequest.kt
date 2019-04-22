package com.martin.sofu.networking

import com.martin.sofu.networking.models.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by martinmistery on 10/29/18.
 */
interface APIRequest {

    @GET("users?page=1&pagesize=30&site=stackoverflow")
    fun getUsers(@Query("page") page: Int,
                 @Query("pagesize") pageSize: Int = 30,
                 @Query("site") site: String = "stackoverflow"): Call<UsersResponse>
}