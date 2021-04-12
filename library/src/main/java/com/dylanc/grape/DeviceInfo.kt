package com.dylanc.grape

import android.os.Build

/**
 * @author Dylan Cai
 */

inline val sdkVersionName: String get() = Build.VERSION.RELEASE

inline val sdkVersionCode get() = Build.VERSION.SDK_INT

inline val deviceManufacturer: String get() = Build.MANUFACTURER

inline val deviceModel: String get() = Build.MODEL
