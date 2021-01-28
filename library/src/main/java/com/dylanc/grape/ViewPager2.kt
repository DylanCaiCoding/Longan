@file:Suppress("unused")

package com.dylanc.grape

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2


/**
 * @author Dylan Cai
 */

fun ViewPager2.setFragmentStateAdapter(fragmentActivity: FragmentActivity, fragmentList: List<Fragment>) {
  adapter = object : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
  }
}

fun ViewPager2.setFragmentStateAdapter(fragment: Fragment, fragmentList: List<Fragment>) {
  adapter = object : FragmentStateAdapter(fragment) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
  }
}

fun ViewPager2.setFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, itemCount: Int, createFragment: (Int) -> Fragment) {
  adapter = object : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = createFragment(position)
  }
}

fun ViewPager2.setFragmentStateAdapter(fragmentActivity: FragmentActivity, itemCount: Int, createFragment: (Int) -> Fragment) {
  adapter = object : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = createFragment(position)
  }
}

fun ViewPager2.setFragmentStateAdapter(fragment: Fragment, itemCount: Int, createFragment: (Int) -> Fragment) {
  adapter = object : FragmentStateAdapter(fragment) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = createFragment(position)
  }
}

fun ViewPager2.setFragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, fragmentList: List<Fragment>) {
  adapter = object : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
  }
}