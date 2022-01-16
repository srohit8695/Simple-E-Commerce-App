package com.example.simpleecommerceapp.ui.cart.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.simpleecommerceapp.R
import com.example.simpleecommerceapp.callbacks.DeleteFromCart
import com.example.simpleecommerceapp.databinding.CartFragmentBinding
import com.example.simpleecommerceapp.database.LocalProducts
import com.example.simpleecommerceapp.ui.cart.CartScreenAdapter
import com.example.simpleecommerceapp.utility.Util

class CartFragment : Fragment(), DeleteFromCart {



    private lateinit var viewModel: CartViewModel
    private var binding: CartFragmentBinding? = null
    private var adapter: CartScreenAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CartFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        adapter = CartScreenAdapter(viewModel.dbList as MutableList<LocalProducts>,inflater.context,viewModel,this)
        binding!!.cartRecyclerView.adapter = adapter
        binding!!.totalAmt.text = Util.priceToIndianConversion(viewModel.totalAmountStr)
        binding!!.checkOut.setOnClickListener {
            checkOutOption()
        }
        var itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.divider
            )!!)
        binding!!.cartRecyclerView.addItemDecoration(itemDecoration)


        return binding!!.root
    }

    fun checkOutOption(){
        Util.showShortToast(requireContext(), "Check Out Initiated")
    }

    override fun onDeleteFromCartClicked() {
        try {
            binding!!.totalAmt.text = Util.priceToIndianConversion(viewModel.totalAmountStr)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}