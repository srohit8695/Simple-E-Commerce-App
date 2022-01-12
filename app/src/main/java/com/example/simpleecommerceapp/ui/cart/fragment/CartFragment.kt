package com.example.simpleecommerceapp.ui.cart.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simpleecommerceapp.R
import com.example.simpleecommerceapp.databinding.CartFragmentBinding
import com.example.simpleecommerceapp.databinding.HomeFragmentBinding
import com.example.simpleecommerceapp.ui.cart.CartScreenAdapter
import com.example.simpleecommerceapp.ui.homescreen.HomeScreenAdapter
import com.example.simpleecommerceapp.utility.Util

class CartFragment : Fragment() {



    private lateinit var viewModel: CartViewModel
    private var binding: CartFragmentBinding? = null
    private var adapter: CartScreenAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CartFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        adapter = CartScreenAdapter(viewModel.dbList,inflater.context)
        binding!!.cartRecyclerView.adapter = adapter
        binding!!.totalAmt.text = viewModel.totalAmountStr//Util.priceToIndianConversion(viewModel.totalAmountStr)
        binding!!.checkOut.setOnClickListener {
            checkOutOption()
        }

        return binding!!.root
    }

    fun checkOutOption(){
        Util.showShortToast(requireContext(), "Check Out Initiated")
    }



}