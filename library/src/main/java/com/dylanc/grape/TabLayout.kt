@file:Suppress("unused")

package com.dylanc.grape

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author Dylan Cai
 */

fun TabLayout.customTab(@LayoutRes layoutId: Int, onBindView: View.(Int) -> Unit) {
  for (i in 0 until tabCount) {
    val tab = getTabAt(i)
    if (tab != null) {
      tab.setCustomView(layoutId)
      onBindView(tab.customView!!, i)
    }
  }
}

fun TabLayout.setupWithViewPager2(
  viewPager: ViewPager2,
  autoRefresh: Boolean = true,
  smoothScroll: Boolean = true,
  tabConfigurationStrategy: (TabLayout.Tab, Int) -> Unit
) {
  viewPager.isUserInputEnabled = smoothScroll
  TabLayoutMediator(this, viewPager, autoRefresh, smoothScroll, tabConfigurationStrategy).attach()
}

fun TabLayout.Tab.setCustomView(@LayoutRes layoutId: Int, onBindView: View.() -> Unit) {
  setCustomView(layoutId)
  onBindView(customView!!)
}

fun <VB : ViewBinding> TabLayout.Tab.setCustomView(
  context: Context,
  inflate: (LayoutInflater) -> VB,
  onBindView: VB.() -> Unit
) {
  val binding = inflate(LayoutInflater.from(context))
  customView = binding.root
  onBindView(binding)
}
