package com.dylanc.longan.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.activityresult.launcher.*
import com.dylanc.longan.*
import com.dylanc.longan.sample.databinding.ActivityLauncherBinding
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
          if (it) {
            cropPictureLauncher.launch(uri) { cropUri ->
              ivPicture.setImageURI(cropUri)
            }
          }
        }
      }
      btnTakePicturePreview.setOnClickListener {
        takePicturePreviewLauncher.launch {
          ivPicture.setImageBitmap(it)
        }
      }
      btnSelectPicture.setOnClickListener {
        getContentLauncher.launchForImage(
          { uri ->
            if (uri != null) {
              binding.ivPicture.setImageURI(uri)
            }
          },
          onPermissionDenied = {
            toast(R.string.no_read_permission)
          }
        )
      }
      btnTakeVideo.setOnClickListener {
        val uri = File(externalMoviesDirPath, "test.mp4").toUri()
        takeVideoLauncher.launch(uri) {
          ivPicture.setImageURI(uri)
        }
      }
      btnPickContact.setOnClickListener {
        pickContactLauncher.launch {

        }
      }
      btnCreateDocument.setOnClickListener {
        createDocumentLauncher.launch(null) {

        }
      }
      btnOpenMultipleDocument.setOnClickListener {
        openMultipleDocumentLauncher.launch(arrayOf()) {

        }
      }
      btnOpenDocumentTree.setOnClickListener {
        openDocumentTreeLauncher.launch(null) {

        }
      }
    }
  }
}