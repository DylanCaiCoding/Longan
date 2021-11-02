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

import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


inline fun TabLayout.setupWithViewPager(
  viewPager: ViewPager,
  autoRefresh: Boolean = true,
  tabConfigurationStrategy: (TabLayout.Tab, Int) -> Unit
) {
  setupWithViewPager(viewPager, autoRefresh)
  for (i in 0 until tabCount) {
    getTabAt(i)?.let { tab ->
      tabConfigurationStrategy(tab, i)
    }
  }
}

fun TabLayout.setupWithViewPager2(
  viewPager: ViewPager2,
  autoRefresh: Boolean = true,
  enableScroll: Boolean = true,
  tabConfigurationStrategy: (TabLayout.Tab, Int) -> Unit
) {
  viewPager.isUserInputEnabled = enableScroll
  TabLayoutMediator(this, viewPager, autoRefresh, enableScroll, tabConfigurationStrategy).attach()
}

inline fun TabLayout.Tab.setCustomView(@LayoutRes layoutId: Int, block: View.() -> Unit) {
  setCustomView(layoutId)
  block(customView!!)
}

inline fun TabLayout.addTab(@StringRes resId: Int, crossinline block: TabLayout.Tab.() -> Unit = {}) =
  addTab(context.getString(resId), block)

inline fun TabLayout.addTab(text: String? = null, crossinline block: TabLayout.Tab.() -> Unit = {}) =
  addTab(newTab().apply { this.text = text }.apply(block))

inline fun TabLayout.doOnTabSelected(crossinline block: (TabLayout.Tab) -> Unit) =
  addOnTabSelectedListener(onTabSelected = block)

inline fun TabLayout.doOnTabUnselected(crossinline block: (TabLayout.Tab) -> Unit) =
  addOnTabSelectedListener(onTabUnselected = block)

inline fun TabLayout.doOnTabReselected(crossinline block: (TabLayout.Tab) -> Unit) =
  addOnTabSelectedListener(onTabReselected = block)

inline fun TabLayout.addOnTabSelectedListener(
  crossinline onTabSelected: (TabLayout.Tab) -> Unit = {},
  crossinline onTabUnselected: (TabLayout.Tab) -> Unit = {},
  crossinline onTabReselected: (TabLayout.Tab) -> Unit = {},
) =
  addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) = onTabSelected(tab)

    override fun onTabUnselected(tab: TabLayout.Tab) = onTabUnselected(tab)

    override fun onTabReselected(tab: TabLayout.Tab) = onTabReselected(tab)
  })
