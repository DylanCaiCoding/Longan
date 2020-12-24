package com.dylanc.grape.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.grape.TakePictureResultLauncher
import com.dylanc.grape.sample.databinding.ActivityMainBinding
import com.dylanc.grape.toast

class MainActivity : AppCompatActivity() {

  private val takePictureResultLauncher = TakePictureResultLauncher()

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btn.setOnClickListener {
      takePictureResultLauncher.launch({
        toast(it)
      }, {
        toast(it)
      })
    }
  }
}