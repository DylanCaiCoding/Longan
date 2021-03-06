package com.dylanc.grape.sample

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.grape.*
import com.dylanc.grape.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SharedPreferencesOwner {

  private val takePictureResultLauncher = TakePictureResultLauncher()
  private val getContentResultLauncher = GetContentResultLauncher()
  private val permissionsResultLauncher = PermissionResultLauncher()
  private val connectedLiveData = NetworkConnectedLiveData()

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.apply {
      btnTakePicture.setOnClickListener {
        takePictureResultLauncher.launch({ path, uri ->
          logDebug(path)
          ivPicture.setImageURI(uri)
        })
      }
      btnSelectPicture.setOnClickListener {
        permissionsResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) {
          if (it) {
            getContentResultLauncher.launch("image/*") { uri ->
              ivPicture.setImageURI(uri)
            }
          }
        }
      }
      btnSecond.setOnClickListener {
        snackbar("Are you sure?") {
          setCustomView(R.layout.layout_custom_snackbar)
          setAction("sure") {
            startActivity<SecondActivity>()
          }
        }
      }
    }
    exitAfterBackPressedTwice("再次点击退出应用")
    connectedLiveData.observe(lifecycleOwner){
      toast(it.toString())
    }
  }

}