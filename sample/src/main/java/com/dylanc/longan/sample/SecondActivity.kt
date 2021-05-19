package com.dylanc.longan.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.longan.NetworkAvailableLiveData
import com.dylanc.longan.intentExtras
import com.dylanc.longan.logDebug
import com.dylanc.longan.pressBackToNotExit

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