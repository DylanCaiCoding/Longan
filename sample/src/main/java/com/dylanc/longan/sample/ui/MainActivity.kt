package com.dylanc.longan.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.longan.pressBackTwiceToExitApp
import com.dylanc.longan.sample.R
import com.dylanc.longan.sample.databinding.ActivityRecyclerViewBinding
import com.dylanc.longan.sample.databinding.ItemTextBinding
import com.dylanc.longan.startActivity
import com.dylanc.viewbinding.base.simpleIntListAdapter
import com.dylanc.viewbinding.binding


class MainActivity : AppCompatActivity() {

  private val binding: ActivityRecyclerViewBinding by binding()
  private val items = listOf(R.string.dialogs, R.string.spannable_string)
  private val adapter = simpleIntListAdapter<ItemTextBinding> {
    tvTitle.setText(it)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.recyclerView.adapter = adapter
    adapter.submitList(items)
    adapter.doOnItemClick { item, _ ->
      when (item) {
        R.string.dialogs -> startActivity<DialogsActivity>()
        R.string.spannable_string -> startActivity<SpannableStringActivity>()
      }
    }
    pressBackTwiceToExitApp(R.string.exit_app)
  }
}