@file:Suppress("unused", "NOTHING_TO_INLINE")

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

inline fun TabLayout.setupWithViewPager(
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

inline fun TabLayout.setupWithViewPager2(
  viewPager: ViewPager2,
  autoRefresh: Boolean = true,
  enableScroll: Boolean = true,
  noinline tabConfigurationStrategy: (TabLayout.Tab, Int) -> Unit
) {
  viewPager.isUserInputEnabled = enableScroll
  TabLayoutMediator(this, viewPager, autoRefresh, enableScroll, tabConfigurationStrategy).attach()
}

inline fun TabLayout.Tab.setCustomView(@LayoutRes layoutId: Int, block: View.() -> Unit) {
  setCustomView(layoutId)
  block(customView!!)
}

inline fun TabLayout.addTab(text: String? = null, block: TabLayout.Tab.() -> Unit = {}) =
  addTab(newTab().apply { this.text = text }.apply(block))

inline fun TabLayout.doOnTabSelected(crossinline block: (TabLayout.Tab) -> Unit) =
  addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {
      block(tab)
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {}

    override fun onTabReselected(tab: TabLayout.Tab) {}
  })

inline fun TabLayout.doOnTabUnselected(crossinline block: (TabLayout.Tab) -> Unit) =
  addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {}

    override fun onTabUnselected(tab: TabLayout.Tab) {
      block(tab)
    }

    override fun onTabReselected(tab: TabLayout.Tab) {}
  })

inline fun TabLayout.doOnTabReselected(crossinline block: (TabLayout.Tab) -> Unit) =
  addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab) {}

    override fun onTabUnselected(tab: TabLayout.Tab) {}

    override fun onTabReselected(tab: TabLayout.Tab) {
      block(tab)
    }
  })