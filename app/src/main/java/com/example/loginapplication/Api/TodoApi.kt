package com.example.loginapplication.Api

import com.example.loginapplication.models.TodoItem
import com.example.loginapplication.models.Todos
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {
    @GET("/todos")
    suspend fun getTodo():Response<Todos>
}