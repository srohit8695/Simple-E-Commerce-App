package com.example.simpleecommerceapp.ui.homescreen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpleecommerceapp.R
import com.example.simpleecommerceapp.databinding.FragmentSplashScreenBinding
import com.example.simpleecommerceapp.databinding.HomeFragmentBinding
import com.example.simpleecommerceapp.utility.SingletonDatas
import com.example.simpleecommerceapp.utility.Util

class HomeFragment : Fragment() {



    private lateinit var viewModel: HomeViewModel
    private var binding: HomeFragmentBinding? = null
//    private val binding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        viewModel.getAllProductList()

        println("rohit "+ viewModel.dataList.value.toString())

       /* if (SingletonDatas.allProducts != null) {

            var productList = SingletonDatas.allProducts

            for (i in 0..productList!!.products!!.size-1){
                println(productList!!.products!!.get(i).toString())
            }

        }*/


        return binding!!.root
    }


}