package com.example.simpleecommerceapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.simpleecommerceapp.models.LocalProducts

@Dao
interface ProductLocalDAO {

    @Insert
    fun insertData(productEntity: LocalProducts) : Long

    @Delete
    fun deleteData(productEntity: LocalProducts)

    @Query("Select * from productTable")
    fun showAllProducts(): LiveData<List<LocalProducts>>

    @Query("SELECT EXISTS (SELECT 1 FROM productTable WHERE product_id = :product_id)")
    fun exists(product_id: Int): Boolean

}