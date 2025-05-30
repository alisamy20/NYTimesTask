package com.example.core_android.base.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.common_kotlin.base.network.NetworkStatusChecker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkChecker @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkStatusChecker {

    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

