@file:Suppress("unused")

package com.dylanc.longan

import android.Manifest.permission.BLUETOOTH
import android.bluetooth.BluetoothAdapter
import androidx.annotation.RequiresPermission


/**
 * @author Dylan Cai
 */

@get:RequiresPermission(BLUETOOTH)
inline val isBluetoothEnabled: Boolean
  get() = BluetoothAdapter.getDefaultAdapter()?.isEnabled == true