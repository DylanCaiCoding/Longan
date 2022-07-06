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

import android.os.Build

inline val isXiaomiRom: Boolean get() = isRomOf("xiaomi")

inline val isHuaweiRom: Boolean get() = isRomOf("huawei")

inline val isOppoRom: Boolean get() = isRomOf("oppo")

inline val isVivoRom: Boolean get() = isRomOf("vivo")

inline val isOnePlusRom: Boolean get() = isRomOf("oneplus")

inline val isSmartisanRom: Boolean get() = isRomOf("smartisan", "deltainno")

inline val isMeiZuRom: Boolean get() = isRomOf("meizu")

inline val isSamsungRom: Boolean get() = isRomOf("samsung")

inline val isGoogleRom: Boolean get() = isRomOf("google")

inline val isSonyRom: Boolean get() = isRomOf("sony")

fun isRomOf(vararg names: String): Boolean =
  names.any { it.contains(Build.BRAND, true) || it.contains(Build.MANUFACTURER, true) }

val isHarmonyOS: Boolean
  get() {
    try {
      val clazz = Class.forName("com.huawei.system.BuildEx")
      val classLoader = clazz.classLoader
      if (classLoader != null && classLoader.parent == null) {
        return clazz.getMethod("getOsBrand").invoke(clazz) == "harmony"
      }
    } catch (e: ClassNotFoundException) {
    } catch (e: NoSuchMethodException) {
    } catch (e: Exception) {
    }
    return false
  }
