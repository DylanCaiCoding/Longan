@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2


/**
 * @author Dylan Cai
 */

inline fun FragmentActivity.FragmentStateAdapter(fragmentList: List<Fragment>) =
  FragmentStateAdapter(fragmentList.size) { fragmentList[it] }

inline fun Fragment.FragmentStateAdapter(fragmentList: List<Fragment>) =
  FragmentStateAdapter(fragmentList.size) { fragmentList[it] }

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

@Suppress("UNCHECKED_CAST")
inline fun <T : Fragment> ViewPager2.findFragment(fragmentManager: FragmentManager, position: Int) =
  fragmentManager.findFragmentByTag("f$position") as T?
