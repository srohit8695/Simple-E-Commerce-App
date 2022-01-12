package com.example.simpleecommerceapp.ui.homescreen.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.utils.Utils
import com.example.simpleecommerceapp.R
import com.example.simpleecommerceapp.callbacks.Resource
import com.example.simpleecommerceapp.callbacks.SwipeHelper
import com.example.simpleecommerceapp.callbacks.SwipeHelperKotlin
import com.example.simpleecommerceapp.databinding.HomeFragmentBinding
import com.example.simpleecommerceapp.models.LocalProducts
import com.example.simpleecommerceapp.models.Product
import com.example.simpleecommerceapp.ui.homescreen.HomeScreenAdapter
import com.example.simpleecommerceapp.utility.Util

class HomeFragment : Fragment() {



    private lateinit var viewModel: HomeViewModel
    private var binding: HomeFragmentBinding? = null
    private var adapter: HomeScreenAdapter? = null

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        adapter = HomeScreenAdapter(emptyList(),inflater.context)
        binding!!.homeRecyclerView.adapter = adapter


        viewModel.apply {
            dataListFromApi.observe(viewLifecycleOwner,{ status ->

                when(status){
                    is Resource.Loading -> {
                        binding!!.shimmerLayout.startShimmer()
                    }

                    is Resource.Success -> {
                        status.data?.let {
                            binding!!.shimmerLayout.stopShimmer()
                            binding!!.shimmerLayout.visibility = View.GONE
                            adapter!!.updateList(it)
                        }
                    }
                    else -> {}
                }
            })
        }


        updateCart()
        binding!!.cart.setOnClickListener {

            if( viewModel.ShowCartProductCount() > 0){
                findNavController().navigate(R.id.action_homeFragment2_to_cartFragment)
            }
            else{
                Util.showShortToast(requireContext(),"NO item in cart")
            }
        }

        object : SwipeHelper(context,binding!!.homeRecyclerView,false) {

            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder?,
                underlayButtons: MutableList<UnderlayButton>?
            ) {

                underlayButtons?.add(UnderlayButton("Wishlist", AppCompatResources.getDrawable(context!!,R.drawable.ic_baseline_favorite_border_24 ), Color.parseColor("#00FF00"), Color.parseColor("#ffffff")
                ) { Util.showShortToast(context!!, "Added to Wishlist") })


                underlayButtons?.add(UnderlayButton("Add Cart", AppCompatResources.getDrawable(context!!,R.drawable.ic_baseline_shopping_cart_24 ), Color.parseColor("#FF0000"), Color.parseColor("#ffffff")) { position ->

                    if(!viewModel.checkProduct(adapter!!.getProductID(position.toString().toInt()).toInt())){
                        if (adapter!!.getProductQty(position.toString().toInt()) > 0) {
                            addToCart(adapter!!.getProduct(position.toString().toInt()))
                        } else {
                            Util.showShortToast(context!!, "Product Quantity must be greater that 0")
                        }
                    }else{
                        Util.showShortToast(context!!, "Product Already Added")
                    }

                })

            }
        }

        return binding!!.root
    }

    fun addToCart(product: Product){

        val localProduct = LocalProducts(product.description, product.id, product.image, product.name, product.price, product.product_id, product.special, product.qty, product.subTotal)

        if(viewModel.insertProduct(localProduct) == viewModel.ShowCartProductCount()){
            Util.showShortToast(requireContext(),"Added to Cart Successful")
            updateCart()
        }else{
            Util.showShortToast(requireContext(),"Added to Cart is Unsuccessful")
        }

    }

    fun updateCart(){
        try {
            val cartCnt = viewModel.ShowCartProductCount().toString()
            binding!!.cart.text = "Cart ("+ cartCnt +")"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }






}