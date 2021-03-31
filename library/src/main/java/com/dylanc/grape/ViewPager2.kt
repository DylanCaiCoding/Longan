@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * @author Dylan Cai
 */

inline fun FragmentStateAdapter(fragmentActivity: FragmentActivity, fragmentList: List<Fragment>) =
  object : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
  }

inline fun FragmentStateAdapter(fragment: Fragment, fragmentList: List<Fragment>) =
  object : FragmentStateAdapter(fragment) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
  }

inline fun FragmentStateAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle,
  itemCount: Int,
  crossinline createFragment: (Int) -> Fragment
) =
  object : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = createFragment(position)
  }

inline fun FragmentStateAdapter(fragmentActivity: FragmentActivity, itemCount: Int, crossinline createFragment: (Int) -> Fragment) =
  object : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = createFragment(position)
  }

inline fun FragmentStateAdapter(fragment: Fragment, itemCount: Int, crossinline createFragment: (Int) -> Fragment) =
  object : FragmentStateAdapter(fragment) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = createFragment(position)
  }

inline fun FragmentStateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, fragmentList: List<Fragment>) =
  object : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
  }
