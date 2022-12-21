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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData

inline val isLocationEnabled: Boolean
  get() = try {
    application.getSystemService<LocationManager>()?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
  } catch (e: Exception) {
    false
  }

class LocationEnabledLiveDate : LiveData<Boolean>() {

  override fun onActive() {
    value = isLocationEnabled
    application.registerReceiver(locationReceiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
  }

  override fun onInactive() {
    application.unregisterReceiver(locationReceiver)
  }

  override fun setValue(value: Boolean?) {
    if (this.value != value) {
      super.setValue(value)
    }
  }

  private val locationReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      value = isLocationEnabled
    }
  }
}
