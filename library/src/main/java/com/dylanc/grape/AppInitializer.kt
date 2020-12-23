@file:Suppress("unused", "ObjectPropertyName")

package com.dylanc.grape

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * @author Dylan Cai
 */

val application: Application get() = AppInitializer.application

internal class AppInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    application = context as Application
    val formatStrategy = PrettyFormatStrategy.newBuilder()
      .methodCount(0)
      .tag("Grape")
      .build()
    Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
  }

  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf()
  }

  companion object {
    internal lateinit var application: Application
  }
}