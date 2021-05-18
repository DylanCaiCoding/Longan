package com.dylanc.grape.sample

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.grape.*
import com.dylanc.grape.sample.databinding.ActivityLauncherBinding
import com.dylanc.viewbinding.binding
import java.io.File


class ResultLauncherActivity : AppCompatActivity(), SharedPreferencesOwner {

  private val takePictureLauncher = TakePictureLauncher()
  private val takeVideoLauncher = TakeVideoLauncher()
  private val pickContactLauncher = PickContactLauncher()
  private val takePicturePreviewLauncher = TakePicturePreviewLauncher()
  private val permissionsLauncher = PermissionLauncher()
  private val getContentLauncher = GetContentLauncher()
  private val createDocumentLauncher = CreateDocumentLauncher()
  private val openMultipleDocumentLauncher = OpenMultipleDocumentsLauncher()
  private val openDocumentTreeLauncher = OpenDocumentTreeLauncher()
  private val cropPictureLauncher = CropPictureLauncher()
  private val binding: ActivityLauncherBinding by binding()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    immerseStatusBar()
    binding.apply {
      toolbar.setNavigationOnClickListener { finish() }
      toolbar.addStatusBarHeightToPaddingTop()
      btnTakePicture.setOnClickListener {
        val uri = File(externalPicturesDirPath, "test.jpg").toUri()
        takePictureLauncher.launch(uri) {
          cropPictureLauncher.launch(uri) {
            ivPicture.setImageURI(it)
          }
        }
      }
      btnTakePicturePreview.setOnClickListener {
        takePicturePreviewLauncher.launch {
          ivPicture.setImageBitmap(it)
        }
      }
      btnSelectPicture.setOnClickListener {
        permissionsLauncher.launch(READ_EXTERNAL_STORAGE,
          onGranted = {
            getContentLauncher.launchForImage { uri ->
              ivPicture.setImageURI(uri)
            }
          }
        )
      }
      btnTakeVideo.setOnClickListener {
        val uri = File(externalMoviesDirPath, "test.mp4").toUri()
        takeVideoLauncher.launchForNullableResult(uri) {
          ivPicture.setImageURI(uri)
        }
      }
      btnPickContact.setOnClickListener {
        pickContactLauncher.launch {
          contentResolver.query(it) { cursor ->
//                val name = cursor.getStringFromColumnIndex(ContactsContract.Profile.DISPLAY_NAME_PRIMARY)
//                toast("$name")
          }
        }
      }
      btnCreateDocument.setOnClickListener {
//        createDocumentLauncher.launch(null) {
//
//        }

//        openDocumentTreeLauncher.launch(null){
//
//        }
        openMultipleDocumentLauncher.launch(arrayOf()) {

        }
//        startActivity<SecondActivity>("test" to 1L)
//            finish()
      }
    }

//        snackbar("Are you sure?") {
//          setCustomView(R.layout.layout_custom_snackbar)
//          setAction("sure") {
//            startActivity<SecondActivity>("test" to 1L)
//            finish()
//          }
//        }
  }
}