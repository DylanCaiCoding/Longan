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

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore


val applicationViewModelStore by lazy { ViewModelStore() }

@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.applicationViewModels(
  noinline factoryProducer: () -> ViewModelProvider.Factory = { defaultViewModelProviderFactory }
): Lazy<VM> =
  createApplicationViewModelLazy(factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> Fragment.applicationViewModels(
  noinline factoryProducer: () -> ViewModelProvider.Factory = { defaultViewModelProviderFactory }
): Lazy<VM> =
  createApplicationViewModelLazy(factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> createApplicationViewModelLazy(
  noinline factoryProducer: () -> ViewModelProvider.Factory =
    { ViewModelProvider.AndroidViewModelFactory.getInstance(application) }
) =
  ViewModelLazy(VM::class, { applicationViewModelStore }, factoryProducer)
