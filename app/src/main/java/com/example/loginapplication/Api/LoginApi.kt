package com.example.loginapplication.Api

import com.example.loginapplication.models.UserX
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {
    @POST("/api/user/user/UserLogin")
    suspend fun getLogin(
        @Query("userName") userName: String,
        @Query("password") password: String,
        @Query("userType") userType: String
    ): Response<UserX>
}