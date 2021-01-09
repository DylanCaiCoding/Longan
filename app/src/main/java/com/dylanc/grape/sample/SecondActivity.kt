package com.dylanc.grape.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.grape.isActivityExistsInStack
import com.dylanc.grape.toast

class SecondActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_second)
//    Handler(Looper.getMainLooper()).postDelayed({
//      finishAllActivities()
//    },1000)

    com.dylanc.grape.finishActivity<MainActivity>()
    toast(isActivityExistsInStack<MainActivity>().toString())
//    toast(topActivity.javaClass.name)
  }
}