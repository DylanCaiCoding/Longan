/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.dylanc.longan

import android.Manifest.permission.BLUETOOTH
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData


@get:RequiresPermission(BLUETOOTH)
inline val isBluetoothEnabled: Boolean
  get() = BluetoothAdapter.getDefaultAdapter()?.isEnabled == true

class BluetoothStateLiveData @RequiresPermission(BLUETOOTH) constructor(
  private val filter: ((BluetoothDevice) -> Boolean)? = null
) : LiveData<Boolean>() {

  private var receiver: BluetoothStateBroadcastReceive? = null

  override fun onActive() {
    val intentFilter = IntentFilter()
    intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
    if (filter != null) {
      intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
      intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
      intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_OFF")
      intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_ON")
    }
    receiver = BluetoothStateBroadcastReceive()
    application.registerReceiver(receiver, intentFilter)
  }

  override fun onInactive() {
    application.unregisterReceiver(receiver)
    receiver = null
  }

  private inner class BluetoothStateBroadcastReceive : BroadcastReceiver() {

    @RequiresPermission(BLUETOOTH)
    override fun onReceive(context: Context, intent: Intent) {
      when (intent.action) {
        BluetoothDevice.ACTION_ACL_CONNECTED ->
          if (filter?.invoke(intent.bluetoothDevice!!) == true) value = true
        BluetoothDevice.ACTION_ACL_DISCONNECTED ->
          if (filter?.invoke(intent.bluetoothDevice!!) == true) value = false
        BluetoothAdapter.ACTION_STATE_CHANGED -> {
          when (intent.bluetoothState) {
            BluetoothAdapter.STATE_OFF -> value = false
            BluetoothAdapter.STATE_ON -> if (filter == null) value = true
          }
        }
      }
    }

    private val Intent.bluetoothState: Int
      get() = getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)

    private val Intent.bluetoothDevice: BluetoothDevice?
      get() = getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
  }
}
