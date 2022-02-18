package com.example.loginapplication.repos

import com.example.loginapplication.Api.RetrofitInstance

class AppRepository {
    suspend fun getLogin(userName:String,password:String,userType:String) = RetrofitInstance.apiLogin.getLogin(userName,password,userType)
    suspend fun getTodo() = RetrofitInstance.apiTodo.getTodo()
}