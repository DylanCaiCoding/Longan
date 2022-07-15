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

class BluetoothEnabledLiveData @RequiresPermission(BLUETOOTH) constructor(
  private val filter: ((BluetoothDevice) -> Boolean)? = null
) : LiveData<Boolean>() {
  private var receiver = BluetoothStateBroadcastReceive()

  override fun onActive() {
    val intentFilter = IntentFilter().apply {
      addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
      if (filter != null) {
        addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        addAction("android.bluetooth.BluetoothAdapter.STATE_OFF")
        addAction("android.bluetooth.BluetoothAdapter.STATE_ON")
      }
    }
    application.registerReceiver(receiver, intentFilter)
    value = isBluetoothEnabled
  }

  override fun onInactive() {
    application.unregisterReceiver(receiver)
  }

  override fun setValue(value: Boolean?) {
    if (this.value != value) {
      super.setValue(value)
    }
  }

  private inner class BluetoothStateBroadcastReceive : BroadcastReceiver() {

    @RequiresPermission(BLUETOOTH)
    override fun onReceive(context: Context, intent: Intent) {
      when (intent.action) {
        BluetoothDevice.ACTION_ACL_CONNECTED ->
          if (filterBluetoothDevice(intent)) value = true
        BluetoothDevice.ACTION_ACL_DISCONNECTED ->
          if (filterBluetoothDevice(intent)) value = false
        BluetoothAdapter.ACTION_STATE_CHANGED -> {
          when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)) {
            BluetoothAdapter.STATE_OFF -> value = false
            BluetoothAdapter.STATE_ON -> if (filter == null) value = true
          }
        }
      }
    }

    private fun filterBluetoothDevice(intent: Intent) =
      intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
        ?.let { device -> filter?.invoke(device) } == true
  }
}
