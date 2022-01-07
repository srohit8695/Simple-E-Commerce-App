package com.example.simpleecommerceapp.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpleecommerceapp.models.Products
import com.example.simpleecommerceapp.networks.ApiInterface
import com.example.simpleecommerceapp.networks.ApiRepository
import com.example.simpleecommerceapp.networks.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashViewModel constructor()  : ViewModel()  {

    val dataList = MutableLiveData<List<Products>>()
    val errorMessage = MutableLiveData<String>()
    val apiInterface = RetrofitService.getInstance()?.create<ApiInterface>(ApiInterface::class.java)

    fun getAllProductList() {

        val call: Call<Products> = apiInterface!!.getAllDatas()
        call?.enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>,response: Response<Products>) {
                if (response.isSuccessful) {
                    val products = response.body()
                    Log.d("rohit",""+response.body().toString())

                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable?) {
                call.cancel()
            }
        })

    }

}