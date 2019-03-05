package com.tranphuc.mvvm.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class AppUtils {
    companion object {
        fun isNetworkReachable(context: Context): Boolean {
            if (context != null) {
                var connectivityManager: ConnectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                var info: Array<NetworkInfo> = connectivityManager.getAllNetworkInfo()
                if (info != null) {
                    for (i in 0..(info.size - 1)) {
                        if (info.get(i).isConnected)
                            return true
                    }
                }
            }
            return false
        }
    }
}