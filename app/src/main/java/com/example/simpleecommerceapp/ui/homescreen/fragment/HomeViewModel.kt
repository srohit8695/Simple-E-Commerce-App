package com.example.simpleecommerceapp.ui.homescreen.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.simpleecommerceapp.callbacks.Resource
import com.example.simpleecommerceapp.database.ProductRepository
import com.example.simpleecommerceapp.database.LocalProducts
import com.example.simpleecommerceapp.models.Product
import com.example.simpleecommerceapp.models.Products
import com.example.simpleecommerceapp.networks.ApiInterface
import com.example.simpleecommerceapp.networks.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (application: Application): AndroidViewModel(application) {

    var dataListFromApi = MutableLiveData<Resource<List<Product>>>()
    val apiInterface: ApiInterface = RetrofitService.getInstance().create<ApiInterface>(ApiInterface::class.java)


    private val repository : ProductRepository = ProductRepository(application)

    init {
        getAllProductList()
    }

    private fun getAllProductList()  {

        dataListFromApi.value = Resource.Loading()

        val call: Call<Products> = apiInterface.getAllDatas()
        call.enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful) {

                    dataListFromApi.value = Resource.Success(response.body()!!.products)

                }
            }

            override fun onFailure(call: Call<Products?>, t: Throwable?) {
                call.cancel()
            }
        })


    }

    fun insertProduct(productEntity: LocalProducts) : Long{
       return repository.insertData(productEntity)
    }

    fun checkProduct(productID : Int) : Boolean{
        return repository.checkProductExist(productID)
    }

    fun showAllProductInCart() : String{
        return repository.getAllData().toString()
    }

    fun showCartProductCount() : Long{
        return repository.totalProductsInCart()
    }

}

