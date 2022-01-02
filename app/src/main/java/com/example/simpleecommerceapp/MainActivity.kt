package com.example.simpleecommerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpleecommerceapp.models.Products
import com.example.simpleecommerceapp.networks.ApiInterface
import com.example.simpleecommerceapp.networks.ApiRepository
import com.example.simpleecommerceapp.networks.RetrofitService
import com.example.simpleecommerceapp.ui.splash.SplashScreen
import com.example.simpleecommerceapp.ui.splash.SplashViewModel
import com.example.simpleecommerceapp.ui.splash.SplashViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: SplashViewModel
//    private val retrofitService = RetrofitService().getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        try {
            val actionBar: ActionBar? = supportActionBar
            if (actionBar != null) actionBar.hide()
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

            replaceFragment(SplashScreen())

        viewModel = ViewModelProvider(this, SplashViewModelFactory()).get(SplashViewModel::class.java)
        viewModel.getAllProductList()

//            val apiInterface = RetrofitService.getInstance()?.create<ApiInterface>(ApiInterface::class.java)

            /*val call: Call<Products> = apiInterface!!.getAllDatas()
            call?.enqueue(object : Callback<Products> {
                override fun onResponse(call: Call<Products>,response: Response<Products>) {
                    if (response.isSuccessful) {
                        Log.d("data",""+response.body())
                    }
                }

                override fun onFailure(call: Call<Products?>, t: Throwable?) {
                    call.cancel()
                }
            })*/

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun replaceFragment(fragmenet : Fragment){
        try {
            val fragmentManager = supportFragmentManager
            val fragmentTranscation = fragmentManager.beginTransaction()
            fragmentTranscation.replace(R.id.fragmentSplash,fragmenet)
            fragmentTranscation.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}