package com.example.simpleecommerceapp.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LOW_PROFILE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.simpleecommerceapp.R
import com.example.simpleecommerceapp.databinding.FragmentSplashScreenBinding
import com.example.simpleecommerceapp.utility.Util
import androidx.navigation.fragment.findNavController
import com.example.simpleecommerceapp.utility.SingletonDatas

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val flags = SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        activity?.window?.decorView?.systemUiVisibility = flags
        (activity as? AppCompatActivity)?.supportActionBar?.hide()



        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        _binding!!.refreshLayout.setOnClickListener {
            _binding!!.refreshLayout.visibility = View.INVISIBLE
            load( container )
        }


        load(container)




        return binding.root

    }

    fun load(container: ViewGroup?){

        Handler().postDelayed({
            if (checkInternetConnection(container!!)) {
                findNavController().navigate(R.id.action_splashScreen_to_homeFragment2)
            } else {
                _binding!!.refreshLayout.visibility = View.VISIBLE
                Util.showShortToast(container.context ,"Check Internet Connectivity")
            }
        }, 2000)

    }

    fun checkInternetConnection(container: ViewGroup?) : Boolean {
        return Util.checkForInternet(container!!.context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onPause() {
        super.onPause()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Clear the systemUiVisibility flag
        activity?.window?.decorView?.systemUiVisibility = 0
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }


    override fun onStop() {
        super.onStop()

    }
}