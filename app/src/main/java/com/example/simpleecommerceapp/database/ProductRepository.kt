package com.example.simpleecommerceapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.example.simpleecommerceapp.models.LocalProducts


class ProductRepository(context: Context) {

    var dbms : ProductLocalDAO = ProductLocalDB.getInstance(context)?.productDao()!!

    fun insertData(productEntity: LocalProducts) : Long{
        return dbms.insertData(productEntity)
    }

    fun deleteData(productEntity: LocalProducts){
        dbms.deleteData(productEntity)
    }

    fun getAllData() : LiveData<List<LocalProducts>> {
        return dbms.showAllProducts()
    }

    fun checkProductExist(id : Int) : Boolean{
        return dbms.exists(id)
    }


}