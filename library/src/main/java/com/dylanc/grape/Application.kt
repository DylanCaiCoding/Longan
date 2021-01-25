package com.dylanc.grape

import android.app.Application

/**
 * @author Dylan Cai
 */

val application: Application get() = AppInitializer.application

val packageName: String get() = application.packageName