package com.example.simpleecommerceapp.models

import com.example.simpleecommerceapp.utility.SingletonDatas

data class Products(
    val products: List<Product>?
)/*{
    init {
        SingletonDatas.allProducts = this
    }
}*/