@file:Suppress("unused")

package com.dylanc.grape

import android.app.Application
import android.content.Context
import androidx.startup.Initializer

/**
 * @author Dylan Cai
 */

private lateinit var application: Application
val app: Application get() = application

class AppInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    application = context as Application
  }

  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf()
  }
}