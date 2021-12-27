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

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.pm.PackageManager

fun applicationMetaDataOf(name: String): String? =
  try {
    application.packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
      .metaData.getString(name)
  } catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
    null
  }

inline fun <reified T : Activity> activityMetaDataOf(name: String): String? =
  try {
    application.packageManager.getActivityInfo(ComponentName(application, T::class.java), PackageManager.GET_META_DATA)
      .metaData.getString(name)
  } catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
    null
  }

inline fun <reified T : Service> serviceMetaDataOf(name: String): String? =
  try {
    application.packageManager.getServiceInfo(ComponentName(application, T::class.java), PackageManager.GET_META_DATA)
      .metaData.getString(name)
  } catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
    null
  }

inline fun <reified T : BroadcastReceiver> providerMetaDataOf(name: String): String? =
  try {
    application.packageManager.getProviderInfo(ComponentName(application, T::class.java), PackageManager.GET_META_DATA)
      .metaData.getString(name)
  } catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
    null
  }

inline fun <reified T : BroadcastReceiver> receiverMetaDataOf(name: String): String? =
  try {
    application.packageManager.getReceiverInfo(ComponentName(application, T::class.java), PackageManager.GET_META_DATA)
      .metaData.getString(name)
  } catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
    null
  }
