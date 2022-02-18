package com.example.loginapplication.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.complete.weatherapplication.utils.Resources
import com.example.loginapplication.repos.AppRepository
import com.example.loginapplication.models.TodoItem
import com.example.loginapplication.models.Todos
import com.example.loginapplication.models.UserX
import kotlinx.coroutines.launch
import retrofit2.Response

class AppViewModel(val repo : AppRepository):ViewModel() {
    var loginUser:MutableLiveData<Resources<UserX>> = MutableLiveData()
    var todos:MutableLiveData<Resources<Todos>> = MutableLiveData()

    fun getLogin(username:String,pass:String, usertype:String) = viewModelScope.launch {
        loginUser.postValue(Resources.Loading())
        val searched = repo.getLogin(username,pass,usertype)
        loginUser.postValue(handleUser(searched))
    }
    private fun handleUser(response: Response<UserX>):Resources<UserX>{
        if(response.isSuccessful){
            response.body()?.let{ resultResponse->
                return Resources.Success(resultResponse)
            }
        }
        return Resources.Error(response.message())
    }
    fun gettodos() = viewModelScope.launch {
        todos.postValue(Resources.Loading())
        val searched = repo.getTodo()
        todos.postValue(handleTodos(searched))
    }
    private fun handleTodos(response: Response<Todos>):Resources<Todos>{
        if(response.isSuccessful){
            response.body()?.let{ resultResponse->
                return Resources.Success(resultResponse)
            }
        }
        return Resources.Error(response.message())
    }
}