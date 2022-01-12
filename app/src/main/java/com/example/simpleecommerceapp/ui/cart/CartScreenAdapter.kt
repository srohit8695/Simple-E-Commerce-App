package com.example.simpleecommerceapp.ui.cart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simpleecommerceapp.databinding.CartScreenItemBinding
import com.example.simpleecommerceapp.databinding.HomeScreenItemBinding
import com.example.simpleecommerceapp.models.LocalProducts
import com.example.simpleecommerceapp.models.Product
import com.example.simpleecommerceapp.ui.homescreen.HomeScreenAdapter
import com.example.simpleecommerceapp.utility.Util

class CartScreenAdapter(private var localList : List<LocalProducts>, private val context: Context) : RecyclerView.Adapter<CartScreenAdapter.LocalProductViewHolder>(){

    inner class LocalProductViewHolder(val binding : CartScreenItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = localList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartScreenAdapter.LocalProductViewHolder {
        return LocalProductViewHolder(CartScreenItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: LocalProductViewHolder, position: Int) {
        with(holder){
            with(localList[position]){
                var qty = 0

                Glide.with(context).load(localList[position].image).into(binding.image)
                binding.name.text = localList[position].name
                binding.pricePerQty.text = localList[position].price
                binding.totalQty.text = localList[position].qty
                binding.totalPrice.text = localList[position].subTotal!!//Util.priceToIndianConversion(localList[position].subTotal!!)

            }
        }
    }


}