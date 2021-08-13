package com.dylanc.longan.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.longan.*
import com.dylanc.longan.sample.adapter.TextAdapter
import com.dylanc.longan.sample.databinding.ActivityMainBinding
import com.dylanc.viewbinding.binding

class MainActivity : AppCompatActivity() {

  private val binding: ActivityMainBinding by binding()
  private val adapter = TextAdapter(::onItemClick)
  private val items = listOf(
    R.string.selector,
    R.string.single_choice_selector,
    R.string.multi_choice_selector,
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.apply {
      immerseStatusBar()
      toolbar.addStatusBarHeightToMarginTop()
      recyclerView.adapter = adapter
      adapter.submitList(items)
    }
    pressBackTwiceToExit("再次点击退出应用")
  }

  private fun onItemClick(id: Int) {
    when (id) {
      R.string.selector -> checkCountry()
      R.string.single_choice_selector -> selectCountry()
      R.string.multi_choice_selector -> selectFoods()
    }
  }

  private fun checkCountry() {
    val countries = listOf("China", "Russia", "USA", "Australia")
    selector(countries, "Where are you from?") { _, i ->
      toast("So you're living in ${countries[i]}, right?")
    }
  }

  private var checkedCountry = "China"
  private fun selectCountry() {
    val countries = listOf("China", "Russia", "USA", "Australia")
    val checkedIndex = countries.indexOfFirst { it == checkedCountry }
    singleChoiceSelector(countries, checkedIndex, "Where are you from?") { dialog, i ->
      checkedCountry = countries[i]
      toast("You're living in ${checkedCountry}.")
      dialog.dismiss()
    }
  }

  private val checkedItems = mutableListOf(false, false, false, false)
  private fun selectFoods() {
    val foods = listOf("Apple", "Banana", "Pear", "Peach")
    multiChoiceSelector(foods, checkedItems, "What do you want to eat?") { _, i, isChecked ->
      checkedItems[i] = isChecked
    }.doOnDismiss {
      toast("So you want to eat ${checkedItems.filter { it }.size} foods.")
    }
  }
}