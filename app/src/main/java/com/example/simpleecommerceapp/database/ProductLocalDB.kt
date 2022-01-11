package com.example.simpleecommerceapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simpleecommerceapp.models.LocalProducts


@Database(entities = [LocalProducts::class], version = 1, exportSchema = false)
@TypeConverters
abstract class ProductLocalDB : RoomDatabase() {

    abstract fun productDao() : ProductLocalDAO

    companion object{

        private var instance : ProductLocalDB ? = null

        fun getInstance(context: Context) : ProductLocalDB?{
            if(instance == null){
                synchronized(ProductLocalDB::class){
                    instance = Room.databaseBuilder(context.applicationContext, ProductLocalDB::class.java, "product.db").allowMainThreadQueries().build()
                }
            }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }
    }

}