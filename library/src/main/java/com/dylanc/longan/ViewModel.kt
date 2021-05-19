@file:Suppress("unused")

package com.dylanc.longan

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

/**
 * @author Dylan Cai
 */

val applicationViewModelStore by lazy { ViewModelStore() }
val applicationViewModelFactory by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(application) }

@MainThread
inline fun <reified VM : ViewModel> applicationViewModels(
  noinline factoryProducer: () -> ViewModelProvider.Factory = { applicationViewModelFactory }
): Lazy<VM> =
  ViewModelLazy(VM::class, { applicationViewModelStore }, factoryProducer)