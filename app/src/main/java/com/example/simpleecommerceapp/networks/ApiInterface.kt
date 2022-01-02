package com.example.simpleecommerceapp.networks

import com.example.simpleecommerceapp.models.Products
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("v2/5def7b172f000063008e0aa2")
    fun getAllDatas() : Call<Products>

}