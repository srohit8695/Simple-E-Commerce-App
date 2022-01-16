package com.example.simpleecommerceapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductLocalDAO {

    @Insert
    fun insertData(productEntity: LocalProducts) : Long

    @Delete
    fun deleteData(productEntity: LocalProducts) : Int

    @Query("Select * from productTable")
    fun showAllProducts(): List<LocalProducts>

    @Query("SELECT EXISTS (SELECT 1 FROM productTable WHERE product_id = :product_id)")
    fun exists(product_id: Int): Boolean


    @Query("SELECT COUNT(*) FROM productTable")
    fun totalProducts(): Long

}