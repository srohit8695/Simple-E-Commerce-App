package com.example.simpleecommerceapp.ui.homescreen.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpleecommerceapp.callbacks.Resource
import com.example.simpleecommerceapp.models.Product
import com.example.simpleecommerceapp.models.Products
import com.example.simpleecommerceapp.networks.ApiInterface
import com.example.simpleecommerceapp.networks.RetrofitService
import com.example.simpleecommerceapp.utility.SingletonDatas
import com.example.simpleecommerceapp.utility.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var dataList = MutableLiveData<Resource<List<Product>>>()
    val apiInterface = RetrofitService.getInstance()?.create<ApiInterface>(ApiInterface::class.java)

    fun getAllProductList()  {

        dataList.value = Resource.Loading()

        val call: Call<Products> = apiInterface.getAllDatas()
        call.enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful) {

                    dataList.value = Resource.Success(response.body()!!.products)

                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable?) {
                call.cancel()
            }
        })


    }

}

