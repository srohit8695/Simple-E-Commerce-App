package com.example.simpleecommerceapp.ui.cart.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.simpleecommerceapp.database.ProductRepository
import com.example.simpleecommerceapp.models.LocalProducts
import com.example.simpleecommerceapp.utility.Util

class CartViewModel(application: Application): AndroidViewModel(application) {

    private val dbRepository : ProductRepository = ProductRepository(application)
    var totalAmountStr = ""
    var dbList : List<LocalProducts>

    init {
        dbList = showAllProductInCart()
    }



    fun showAllProductInCart() : List<LocalProducts>{
        val allProducts = dbRepository.getAllData()
        calculateTotal(allProducts)
        return allProducts
    }

    fun calculateTotal( allProducts : List<LocalProducts>){
        var totalAmount = 0.0
        try {
            for(i in 0..allProducts.size-1){
                totalAmount += Util.stringToFloat(allProducts.get(i).subTotal!!)
            }
            totalAmountStr = totalAmount.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}