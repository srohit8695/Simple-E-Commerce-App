package com.example.simpleecommerceapp.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.example.simpleecommerceapp.models.Products
import java.text.NumberFormat
import java.util.*
import kotlin.random.Random

class Util {

    companion object{

        fun checkForInternet(context: Context): Boolean {

            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                // Returns a Network object corresponding to
                // the currently active default data network.
                val network = connectivityManager.activeNetwork ?: return false

                // Representation of the capabilities of an active network.
                val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    // Indicates this network uses a Wi-Fi transport,
                    // or WiFi has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                    // Indicates this network uses a Cellular transport. or
                    // Cellular has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                    // else return false
                    else -> false
                }
            } else {
                // if the android version is below M
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        }


        fun showShortToast(context: Context, message: String){
            Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
        }


        fun stringToFloat( price : String) : Float{
            var charPrice = ""

            for (i in price.indices){
                if((price[i] in '0'..'9') || price[i] == '.'){
                    charPrice += price[i]
                }
            }

            return charPrice.toFloat()
        }


        fun calculateTotalFromQty(qty : Int, price : Float) : Float{
            return  qty * price
        }

        fun randomFloat(from : Float, to : Float) : Float{
            return Random.nextDouble(from.toDouble(),to.toDouble()).toFloat()
        }

        fun randomInt(from : Int, to : Int) : Int{
            return Random.nextInt(from,to)
        }

        fun priceToIndianConversion(price : String) : String{
            val formatter = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
            return formatter.format(price.toFloat())
        }

    }

}