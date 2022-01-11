package com.example.simpleecommerceapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "productTable")
data class LocalProducts(
    val description: String?,
    val id: String?,
    val image: String?,
    val name: String?,
    val price: String?,
    val product_id: String?,
    val special: String?,
    var qty : String?,
    var subTotal : String?,
    @PrimaryKey(autoGenerate = true) val product_local_id: Int?= null
)