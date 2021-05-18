package com.dylanc.grape.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.grape.NetworkAvailableLiveData
import com.dylanc.grape.intentExtras
import com.dylanc.grape.logDebug
import com.dylanc.grape.pressBackToNotExit

class SecondActivity : AppCompatActivity() {
  private val test: Long by intentExtras("test", -1)
  private val networkAvailableLiveData = NetworkAvailableLiveData()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_second)
    logDebug(test.toString())
    pressBackToNotExit()
    networkAvailableLiveData.observe(this) {
      logDebug(it.toString())
    }
  }
}