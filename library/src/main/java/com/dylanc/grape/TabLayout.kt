@file:Suppress("unused")

package com.dylanc.grape

import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author Dylan Cai
 */

fun TabLayout.setupWithViewPager(
  viewPager: ViewPager,
  autoRefresh: Boolean = true,
  tabConfigurationStrategy: (TabLayout.Tab, Int) -> Unit
) {
  setupWithViewPager(viewPager, autoRefresh)
  for (i in 0 until tabCount) {
    val tab = getTabAt(i)
    if (tab != null) {
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

fun TabLayout.Tab.setCustomView(@LayoutRes layoutId: Int, onBindView: View.() -> Unit) {
  setCustomView(layoutId)
  onBindView(customView!!)
}

fun TabLayout.addTab(text: String? = null, block: TabLayout.Tab.() -> Unit = {}) =
  addTab(newTab().apply { this.text = text }.apply(block))

fun TabLayout.doOnTabSelected(action: (TabLayout.Tab) -> Unit) =
  addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {
      action(tab)
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {}

    override fun onTabReselected(tab: TabLayout.Tab) {}
  })

fun TabLayout.onTabUnselected(action: (TabLayout.Tab) -> Unit) =
  addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {}

    override fun onTabUnselected(tab: TabLayout.Tab) {
      action(tab)
    }

    override fun onTabReselected(tab: TabLayout.Tab) {}
  })

fun TabLayout.onTabReselected(action: (TabLayout.Tab) -> Unit) =
  addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {}

    override fun onTabUnselected(tab: TabLayout.Tab) {}

    override fun onTabReselected(tab: TabLayout.Tab) {
      action(tab)
    }
  })