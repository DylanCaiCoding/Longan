package com.dylanc.grape.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.grape.addStatusBarHeightToPaddingTop
import com.dylanc.grape.immerseStatusBar
import com.dylanc.grape.pressBackTwiceToExit
import com.dylanc.grape.sample.adapter.TextAdapter
import com.dylanc.grape.sample.databinding.ActivityMainBinding
import com.dylanc.grape.startActivity
import com.dylanc.viewbinding.binding

class MainActivity : AppCompatActivity() {

  private val binding: ActivityMainBinding by binding()
  private val adapter = TextAdapter(::onItemClick)
  private val items = listOf(
    R.string.activity_result,
    R.string.share,
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    immerseStatusBar()
    binding.apply {
      toolbar.addStatusBarHeightToPaddingTop()
      recyclerView.adapter = adapter
      adapter.submitList(items)
    }
    pressBackTwiceToExit("再次点击退出应用")
  }

  private fun onItemClick(id: Int) {
    when (id) {
      R.string.activity_result -> startActivity<ResultLauncherActivity>()
      R.string.share -> startActivity<ResultLauncherActivity>()
    }
  }
}