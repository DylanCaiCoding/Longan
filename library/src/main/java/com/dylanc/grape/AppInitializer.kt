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

private lateinit var _application: Application
val application: Application get() = _application

class AppInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    _application = context as Application
    val formatStrategy = PrettyFormatStrategy.newBuilder()
      .methodCount(0)
      .tag("Logger")
      .build()
    Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
      override fun isLoggable(priority: Int, tag: String?): Boolean {
        return LoggerConfig.isLoggable(priority, tag)
      }
    })
  }

  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf()
  }
}