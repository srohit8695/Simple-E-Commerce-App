package com.example.simpleecommerceapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simpleecommerceapp.networks.ApiRepository
import com.example.simpleecommerceapp.networks.RetrofitService


class SplashViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            SplashViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}