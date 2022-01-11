package com.example.simpleecommerceapp.networks

import com.example.simpleecommerceapp.models.Products
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

import okhttp3.logging.HttpLoggingInterceptor




class RetrofitService{

    companion object{
        val BASE_URL: String = "https://www.mocky.io/"

        fun getInstance() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }

}