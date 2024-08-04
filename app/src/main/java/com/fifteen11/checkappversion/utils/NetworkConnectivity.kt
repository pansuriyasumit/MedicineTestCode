package com.fifteen11.checkappversion.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.InetAddress
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A LiveData class that monitors network connectivity.
 *
 * This class uses the ConnectivityManager to register a network callback and updates
 * its value based on the availability of the network. It extends LiveData&lt;Boolean&gt;
 * to provide a reactive way to observe network connectivity changes.
 */
@Singleton
class NetworkConnectivity @Inject constructor(@ApplicationContext context: Context): LiveData<Boolean>() {

    // ConnectivityManager to handle network-related operations
    private val connectivityManager: ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)

    // NetworkCallback to listen for network changes
    private val networkCallback = object : NetworkCallback() {

        // Called when the network becomes available
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(isNetworkAvailable())
        }

        // Called when the network becomes unavailable
        override fun onUnavailable() {
            super.onUnavailable()
            postValue(false)
        }

        // Checks if the network is available by trying to resolve an address
        private fun isNetworkAvailable(): Boolean {
            return try {
                val address = InetAddress.getByName("www.google.com")
                address.toString() != ""
            } catch (ex: UnknownHostException) {
                false
            }
        }
    }

    init {
        // Initialize the LiveData with the current network state
        this.value = (connectivityManager.activeNetwork != null)

        // Register the network callback to listen for network changes
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            networkCallback
        )
    }

    /**
     * Returns the current network connectivity status.
     *
     * @return true if the device is connected to the internet, false otherwise
     */
    fun isConnected(): Boolean {
        return value == true
    }
}