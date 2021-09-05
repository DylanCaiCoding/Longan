@file:Suppress("unused", "FunctionName")

package com.dylanc.longan

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

/**
 * @author Dylan Cai
 */

val applicationViewModelStore by lazy { ViewModelStore() }

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.applicationViewModels(
  noinline factoryProducer: () -> ViewModelProvider.Factory =
    { ViewModelProvider.AndroidViewModelFactory.getInstance(application) }
): Lazy<VM> =
  createApplicationViewModelLazy(factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> Fragment.applicationViewModels(
  noinline factoryProducer: () -> ViewModelProvider.Factory =
    { ViewModelProvider.AndroidViewModelFactory.getInstance(application) }
): Lazy<VM> =
  createApplicationViewModelLazy(factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> ViewModel.applicationViewModels(
  noinline factoryProducer: () -> ViewModelProvider.Factory =
    { ViewModelProvider.AndroidViewModelFactory.getInstance(application) }
): Lazy<VM> =
  createApplicationViewModelLazy(factoryProducer)

inline fun <reified VM : ViewModel> createApplicationViewModelLazy(
  noinline factoryProducer: () -> ViewModelProvider.Factory =
    { ViewModelProvider.AndroidViewModelFactory.getInstance(application) }
) =
  ViewModelLazy(VM::class, { applicationViewModelStore }, factoryProducer)
