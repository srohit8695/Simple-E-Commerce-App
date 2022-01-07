package com.example.simpleecommerceapp.ui.homescreen.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    var dataList = MutableLiveData<List<Product>>()
    val errorMessage = MutableLiveData<String>()
    val apiInterface = RetrofitService.getInstance()?.create<ApiInterface>(ApiInterface::class.java)

    fun getAllProductList()  {

        val call: Call<Products> = apiInterface!!.getAllDatas()
        call?.enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful) {

                    dataList.value = response.body()!!.products

//                    SingletonDatas.name = "rohit"

                    /*var productList = response.body()

                    for (i in 0..productList!!.products!!.size-1){
                        println(productList!!.products!!.get(i).toString())
                    }*/

                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable?) {
                call.cancel()
            }
        })


    }

}

