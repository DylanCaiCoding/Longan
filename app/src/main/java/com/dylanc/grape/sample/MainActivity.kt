package com.dylanc.grape.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.grape.TakePictureResultLauncher
import com.dylanc.grape.logDebug
import com.dylanc.grape.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private val takePictureResultLauncher = TakePictureResultLauncher()

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.apply {
      btnTakePicture.setOnClickListener {
        takePictureResultLauncher.launch({ file, uri ->
          logDebug(file.path)
          ivPicture.setImageURI(uri)
        })
      }
    }
  }
}