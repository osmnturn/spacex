package com.spacex.util

import android.content.Context
import android.net.ConnectivityManager


class NetworkUtil private constructor() {

    private object Holder {
        val INSTANCE = NetworkUtil()
    }

    companion object {
        @JvmStatic
        val instance: NetworkUtil by lazy { Holder.INSTANCE }

    }


    fun isConnectingToInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

}
