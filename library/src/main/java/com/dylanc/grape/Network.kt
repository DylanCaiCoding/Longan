@file:Suppress("unused")

package com.dylanc.grape

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.INTERNET
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData

/**
 * @author Dylan Cai
 */

class NetworkConnectedLiveData @RequiresPermission(ACCESS_NETWORK_STATE) constructor() : LiveData<Boolean>() {

  private val connectivityManager: ConnectivityManager =
    application.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

  private val networkRequest by lazy {
    NetworkRequest.Builder()
      .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
      .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
      .build()
  }

  @RequiresPermission(ACCESS_NETWORK_STATE)
  override fun onActive() {
    super.onActive()
    when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
        connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback)
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ->
        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
    }
  }

  override fun onInactive() {
    super.onInactive()
    connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
  }

  private val connectivityManagerCallback by lazy {
    object : ConnectivityManager.NetworkCallback() {
      override fun onAvailable(network: Network) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
          Build.VERSION.SDK_INT < Build.VERSION_CODES.M
        ) {
          postValue(true)
        }
      }

      override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          networkCapabilities.run {
            if (hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
              hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            ) {
              postValue(true)
            }
          }
        }
      }

      override fun onLost(network: Network) {
        postValue(false)
      }
    }
  }
}

class NetworkAvailableLiveData @RequiresPermission(INTERNET) constructor() : LiveData<Boolean>() {

  override fun onActive() {
    super.onActive()
  }

  override fun onInactive() {
    super.onInactive()
  }

}