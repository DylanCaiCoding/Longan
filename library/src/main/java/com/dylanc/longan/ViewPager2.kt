@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * @author Dylan Cai
 */

inline fun FragmentActivity.FragmentStateAdapter(fragmentList: List<Fragment>) =
  object : FragmentStateAdapter(this) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
  }

inline fun Fragment.FragmentStateAdapter(fragmentList: List<Fragment>) =
  object : FragmentStateAdapter(this) {
    override fun getItemCount() = fragmentList.size
    override fun createFragment(position: Int) = fragmentList[position]
  }

inline fun FragmentActivity.FragmentStateAdapter(itemCount: Int, crossinline block: (Int) -> Fragment) =
  object : FragmentStateAdapter(this) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = block(position)
  }

inline fun Fragment.FragmentStateAdapter(itemCount: Int, crossinline block: (Int) -> Fragment) =
  object : FragmentStateAdapter(this) {
    override fun getItemCount() = itemCount
    override fun createFragment(position: Int) = block(position)
  }