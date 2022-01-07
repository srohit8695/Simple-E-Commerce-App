package com.example.simpleecommerceapp.utility

import com.example.simpleecommerceapp.models.Products

class SingletonDatas {

    companion object{
         val instance = SingletonDatas()
    }


    var allProducts : Products? = null
    var name : String? = null

}