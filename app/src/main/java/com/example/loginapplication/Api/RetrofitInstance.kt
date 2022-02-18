package com.example.loginapplication.Api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {
    companion object{
        private val retrofitTodo by lazy{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging).build()
            Retrofit.Builder().run {
                addConverterFactory(GsonConverterFactory.create())
                baseUrl("https://jsonplaceholder.typicode.com")
                client(client)
                build()
            }
        }
        private val retrofitLogin by lazy{
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging).build()
            Retrofit.Builder().run {
                addConverterFactory(GsonConverterFactory.create())
                baseUrl("https://uatrelwebapi.azurewebsites.net")
                client(client)
                build()
            }
        }
        val apiLogin by lazy{
            retrofitLogin.create(LoginApi::class.java)
        }
        val apiTodo by lazy{
            retrofitTodo.create((TodoApi::class.java))
        }
    }
}