package com.example.simpleecommerceapp.ui.homescreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simpleecommerceapp.databinding.HomeScreenItemBinding
import com.example.simpleecommerceapp.models.Product

class HomeScreenAdapter(private var dataList : List<Product>, private val context: Context) : RecyclerView.Adapter<HomeScreenAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(val binding : HomeScreenItemBinding ) : RecyclerView.ViewHolder(binding.root)

    fun updateList(updatedList: List<Product>){
        dataList = updatedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(HomeScreenItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder){
            with(dataList[position]){
                Glide.with(context).load(dataList[position].image).into(binding.image)
                binding.name.text = dataList[position].name
            }
        }
    }

    override fun getItemCount(): Int = dataList.size


}