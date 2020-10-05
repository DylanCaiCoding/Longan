@file:Suppress("unused")

package com.dylanc.grape

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * @author Dylan Cai
 */

private lateinit var application: Application
val app: Application get() = application

class AppInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    application = context as Application
    Logger.addLogAdapter(AndroidLogAdapter())
  }

  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf()
  }
}