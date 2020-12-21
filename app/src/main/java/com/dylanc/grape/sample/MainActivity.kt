package com.dylanc.grape.sample

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.grape.PermissionResultLauncher
import com.dylanc.grape.logDebug
import com.dylanc.grape.toast

class MainActivity : AppCompatActivity() {

  private val launcher = PermissionResultLauncher()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    launcher.launch(Manifest.permission.CAMERA) {
      if (it)
        toast("success")
      else
        toast("failure")
    }
  }
}