package com.example.simpleecommerceapp.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simpleecommerceapp.callbacks.DeleteFromCart
import com.example.simpleecommerceapp.databinding.CartScreenItemBinding
import com.example.simpleecommerceapp.database.LocalProducts
import com.example.simpleecommerceapp.ui.cart.fragment.CartViewModel
import com.example.simpleecommerceapp.utility.Util

class CartScreenAdapter(private var localList : MutableList<LocalProducts>, private val context: Context, private val cartViewModel: CartViewModel, private val deleteFromCart: DeleteFromCart) : RecyclerView.Adapter<CartScreenAdapter.LocalProductViewHolder>(){

    inner class LocalProductViewHolder(val binding : CartScreenItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = localList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartScreenAdapter.LocalProductViewHolder {
        return LocalProductViewHolder(CartScreenItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: LocalProductViewHolder, position: Int) {
        try {
            with(holder){
                with(localList[position]){
                    var qty = 0

                    Glide.with(context).load(localList[position].image).into(binding.image)
                    binding.name.text = localList[position].name
                    binding.pricePerQty.text = localList[position].price
                    binding.totalQty.text = localList[position].qty
                    binding.totalPrice.text = localList[position].subTotal!!
                    binding.delete.setOnClickListener {

                        val builder = AlertDialog.Builder(context)
                        builder.setMessage("Wish to remove from cart.")
                        builder.setPositiveButton("Yes") { dialog, which ->
                            if(cartViewModel.deleteItemInCart(localList[position]) == 1){
                                localList.removeAt(position)
                                Util.showShortToast(context,"Removed from Cart Successfully")
                                notifyDataSetChanged()
                                deleteFromCart.onDeleteFromCartClicked()
                            }
                            else{
                                Util.showShortToast(context,"Removed from Cart Unsuccessfully")
                            }
                        }
                        builder.setNegativeButton("No") { dialog, which ->

                        }
                        builder.show()
                    }

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun refreshAfterDelete(position : Int){
        notifyItemRemoved(position)
    }


}