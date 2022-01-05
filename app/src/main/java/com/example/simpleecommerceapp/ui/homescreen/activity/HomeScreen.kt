package com.example.simpleecommerceapp.ui.homescreen.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.simpleecommerceapp.R
import com.example.simpleecommerceapp.ui.homescreen.fragment.HomeFragment

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

//        replaceFragment(HomeFragment())

    }

    /*fun replaceFragment(fragmenet: Fragment){
        try {
            val fragmentManager = supportFragmentManager
            val fragmentTranscation = fragmentManager.beginTransaction()
            fragmentTranscation.replace(R.id.homeFragment,fragmenet)
            fragmentTranscation.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

}