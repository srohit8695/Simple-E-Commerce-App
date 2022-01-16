package com.example.simpleecommerceapp.database

import android.content.Context


class ProductRepository(context: Context) {

    var dbms : ProductLocalDAO = ProductLocalDB.getInstance(context)?.productDao()!!

    fun insertData(productEntity: LocalProducts) : Long{
        return dbms.insertData(productEntity)
    }

    fun deleteData(productEntity: LocalProducts) : Int{
        return dbms.deleteData(productEntity)
    }

    fun getAllData() : List<LocalProducts> {
        return dbms.showAllProducts()
    }

    fun checkProductExist(id : Int) : Boolean{
        return dbms.exists(id)
    }

    fun totalProductsInCart() : Long{
        return dbms.totalProducts()
    }

}