@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.grape

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @author Dylan Cai
 */


inline fun <reified T : Activity> FragmentActivity.startActivityForResult(
  requestCode: Int,
  vararg extras: Pair<String, *>,
  noinline callback: (resultCode: Int, data: Intent?) -> Unit
) = startActivityForResult(intentOf<T>(*extras), requestCode, callback)

inline fun FragmentActivity.startActivityForResult(
  intent: Intent,
  requestCode: Int,
  noinline callback: (resultCode: Int, data: Intent?) -> Unit
) =
  DispatchResultFragment.newInstance(this).startForResult(intent, requestCode, callback)


class DispatchResultFragment : Fragment() {

  companion object {
    private const val TAG = "dispatch_result"

    fun newInstance(activity: FragmentActivity): DispatchResultFragment =
      activity.run {
        val fragmentManager = this.supportFragmentManager
        var fragment: DispatchResultFragment? =
          fragmentManager.findFragmentByTag(DispatchResultFragment.TAG) as DispatchResultFragment?
        if (fragment == null) {
          fragment = DispatchResultFragment()
          fragmentManager
            .beginTransaction()
            .add(fragment, DispatchResultFragment.TAG)
            .commitAllowingStateLoss()
          fragmentManager.executePendingTransactions()
        }
        fragment
      }
  }

  private val callbacks = SparseArray<(resultCode: Int, data: Intent?) -> Unit>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  fun startForResult(
    intent: Intent,
    requestCode: Int,
    callback: (resultCode: Int, data: Intent?) -> Unit
  ) {
    callbacks.put(requestCode, callback)
    startActivityForResult(intent, requestCode)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    val callback = callbacks.get(requestCode)
    if (callback != null) {
      callback.invoke(resultCode, data)
      callbacks.remove(requestCode)
    }
  }
}

