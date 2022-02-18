package com.example.loginapplication.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loginapplication.repos.AppRepository

class AppViewModelFactory(val repo: AppRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel(repo) as T
    }
}