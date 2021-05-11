@file:Suppress("unused")

package com.dylanc.grape

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.*
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData


/**
 * @author Dylan Cai
 */

@Suppress("DEPRECATION")
@get:RequiresPermission(ACCESS_NETWORK_STATE)
inline val isNetworkAvailable: Boolean
  get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
      hasCapability(NET_CAPABILITY_INTERNET) && hasCapability(NET_CAPABILITY_VALIDATED)
    }
  } else {
    connectivityManager.activeNetworkInfo?.isConnectedOrConnecting
  } ?: false

@RequiresPermission(ACCESS_NETWORK_STATE)
fun isNetworkAvailable2() = false

class NetworkAvailableLiveData @RequiresPermission(ACCESS_NETWORK_STATE) constructor() : LiveData<Boolean>() {

  @RequiresPermission(ACCESS_NETWORK_STATE)
  override fun onActive() {
    super.onActive()
    when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ->
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }
  }

  override fun onInactive() {
    super.onInactive()
    connectivityManager.unregisterNetworkCallback(networkCallback)
  }

  private val networkRequest by lazy {
    NetworkRequest.Builder()
      .addTransportType(TRANSPORT_CELLULAR)
      .addTransportType(TRANSPORT_ETHERNET)
      .addTransportType(TRANSPORT_WIFI)
      .build()
  }

  private val networkCallback by lazy {
    object : ConnectivityManager.NetworkCallback() {
      override fun onAvailable(network: Network) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
          postValue(true)
        }
      }

      override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          networkCapabilities.run {
            postValue(hasCapability(NET_CAPABILITY_INTERNET) && hasCapability(NET_CAPABILITY_VALIDATED))
          }
        }
      }

      override fun onLost(network: Network) {
        postValue(false)
      }
    }
  }
}