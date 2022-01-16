package com.example.simpleecommerceapp.ui.homescreen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simpleecommerceapp.databinding.HomeScreenItemBinding
import com.example.simpleecommerceapp.models.Product
import com.example.simpleecommerceapp.utility.Util
import kotlin.random.Random

class HomeScreenAdapter(private var dataList : List<Product>, private val context: Context) : RecyclerView.Adapter<HomeScreenAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(val binding : HomeScreenItemBinding ) : RecyclerView.ViewHolder(binding.root)

    fun updateList(updatedList: List<Product>){
        dataList = updatedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(HomeScreenItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder){

            try {
                with(dataList[position]){
                    var qty = 0

                    if (dataList[position].qty.toString().trim().equals("0")) {
                        dataList[position].qty = qty.toString()
                        dataList[position].subTotal = "0"
                    } else {
                        qty = dataList[position].qty?.toInt() ?: 0
                    }

                    Glide.with(context).load(dataList[position].image).into(binding.image)
                    binding.name.text = dataList[position].name
                    binding.originalPrice.text = dataList[position].price
                    binding.originalPrice.paintFlags = binding.originalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.specialPrice.text = "  " + dataList[position].special + "  "

                    val cp = Util.stringToFloat((binding.originalPrice.text as String?)!!)
                    val sp = Util.stringToFloat((binding.specialPrice.text as String?)!!)
                    val saved = ((cp - sp)/cp) * 100
                    binding.saved.text = String.format("%.1f", saved)+"% OFF"
                    binding.totalQty.text = qty.toString()

                    binding.add.setOnClickListener {
                        ++qty
                        updateQty(qty, binding)
                        binding.totalPrice.text = Util.priceToIndianConversion(Util.calculateTotalFromQty(qty, sp).toString())
                        dataList[position].subTotal = binding.totalPrice.text.toString()
                        dataList[position].qty = qty.toString()
                    }

                    binding.remove.setOnClickListener {
                        if(qty >= 1){
                            --qty
                            updateQty(qty, binding)
                            binding.totalPrice.text = Util.priceToIndianConversion(Util.calculateTotalFromQty(qty, sp).toString())
                            dataList[position].subTotal = binding.totalPrice.text.toString()
                            if(qty == 0){
                                binding.totalPrice.text = ""
                            }
                            dataList[position].qty = qty.toString()
                        }else if(qty == 0){
                            Util.showShortToast(context,"Cannot be reduced further")
                        }
                    }

                    /*binding.ratingBar.rating = Util.randomFloat(0.1F, 4.8F)
                    ("  ( "+ Util.randomInt(112,8587).toString() + " )").also { binding.ratingBarTextView.text = it }*/


                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQty(qty : Int,  binding : HomeScreenItemBinding) {
        if(qty == 1){
            Util.showShortToast(context,"Swipe to Add to Cart")
        }
        binding.totalQty.text = qty.toString()


    }

    override fun getItemCount(): Int = dataList.size


    fun getProductID(position: Int) : String{
        return dataList[position].product_id.toString()
    }

    fun getProduct(position: Int) : Product{
       return dataList[position]
    }

    fun getProductQty(position: Int) : Int {
        return dataList[position].qty!!.toInt()
    }



}