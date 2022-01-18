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

package com.dylanc.longan.design

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

fun FragmentActivity.FragmentStateAdapter(vararg fragments: Fragment, isLazyLoading: Boolean = false): FragmentStateAdapter =
  FragmentStateAdapter(fragments.size, isLazyLoading) { fragments[it] }

fun Fragment.FragmentStateAdapter(vararg fragments: Fragment, isLazyLoading: Boolean = false): FragmentStateAdapter =
  FragmentStateAdapter(fragments.size, isLazyLoading) { fragments[it] }

fun FragmentActivity.FragmentStateAdapter(itemCount: Int, isLazyLoading: Boolean = false, block: (Int) -> Fragment): FragmentStateAdapter =
  FragmentStateAdapter(supportFragmentManager, lifecycle, itemCount, isLazyLoading, block)

fun Fragment.FragmentStateAdapter(itemCount: Int, isLazyLoading: Boolean = false, block: (Int) -> Fragment): FragmentStateAdapter =
  FragmentStateAdapter(childFragmentManager, lifecycle, itemCount, isLazyLoading, block)

fun FragmentStateAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle,
  itemCount: Int,
  isLazyLoading: Boolean = false,
  block: (Int) -> Fragment
) =
  object : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = block(position)
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
      super.onAttachedToRecyclerView(recyclerView)
      if (isLazyLoading) recyclerView.setItemViewCacheSize(itemCount)
    }
  }

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> ViewPager2.findFragment(fragmentManager: FragmentManager, position: Int) =
  fragmentManager.findFragmentByTag("f$position") as T?
