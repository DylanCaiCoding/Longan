/*
 * Copyright (C) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dylanc.longan.sample.ui

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.OpenableColumns
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.dylanc.longan.activityresult.*
import com.dylanc.longan.design.alert
import com.dylanc.longan.design.cancelButton
import com.dylanc.longan.design.okButton
import com.dylanc.longan.design.selector
import com.dylanc.longan.sample.R
import com.dylanc.longan.sample.databinding.ActivityResultBinding
import com.dylanc.longan.sample.ui.InputTextResultContract.Companion.KEY_NAME
import com.dylanc.longan.sample.ui.InputTextResultContract.Companion.KEY_TITLE
import com.dylanc.longan.size
import com.dylanc.longan.toast
import com.dylanc.viewbinding.binding

/**
 * @author Dylan Cai
 */
class ActivityResultActivity : AppCompatActivity() {
  private val binding: ActivityResultBinding by binding()
  private var uri: Uri? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setTitle(R.string.activity_result)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    binding.apply {
      btnStartActivity.setOnClickListener {
        startActivityLauncher.launch<InputTextActivity>(KEY_NAME to "nickname", KEY_TITLE to "Nickname")
      }
      btnTakePicturePreview.setOnClickListener { takePicturePreviewLauncher.launch() }
      btnTakePicture.setOnClickListener { uri = takePictureLauncher.launchAndSaveToCache() }
      btnCropPicture.setOnClickListener { cropPictureLauncher.launch(uri!!) }
      btnTakeVideo.setOnClickListener { uri = takeVideoLauncher.launchAndSaveToCache() }
      btnPickPicture.setOnClickListener { pickContentLauncher.launchForImage() }
      btnPickVideo.setOnClickListener { pickContentLauncher.launchForVideo() }
      btnGetMultiplePicture.setOnClickListener { getMultipleContentsLauncher.launchForImage() }
      btnGetMultipleVideo.setOnClickListener { getMultipleContentsLauncher.launchForVideo() }
      btnRequestPermission.setOnClickListener { requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) }
      btnRequestMultiplePermissions.setOnClickListener {
        requestMultiplePermissionsLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE)
      }
      btnAppDetailsSettings.setOnClickListener { appSettingsLauncher.launch() }
      btnCreateDocument.setOnClickListener { createDocumentLauncher.launch(null) }
      btnOpenDocument.setOnClickListener { openDocumentLauncher.launch("application/*") }
      btnOpenMultipleDocument.setOnClickListener { openMultipleDocumentsLauncher.launch("application/*") }
      btnOpenDocumentTree.setOnClickListener { openDocumentTreeLauncher.launch(null) }
      btnPickContact.setOnClickListener { pickContactLauncher.launch() }
      btnEnableBluetooth.setOnClickListener { enableBluetoothLauncher.launch() }
      btnEnableLocation.setOnClickListener { enableLocationLauncher.launch() }
      btnInputText.setOnClickListener { inputTextLauncher.launch(InputTextRequest("Nickname")) }
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }

  private val startActivityLauncher = registerForStartActivityResult { result ->
    if (result.resultCode == RESULT_OK) {
      result.data?.getStringExtra("value")?.let { toast(it) }
    }
  }

  private val takePicturePreviewLauncher = registerForTakePicturePreviewResult { bitmap ->
    if (bitmap != null) {
      PictureActivity.start(bitmap)
    }
  }

  private val takePictureLauncher = registerForTakePictureResult { takeSuccess ->
    if (takeSuccess && uri != null) {
      PictureActivity.start(uri!!)
    }
  }

  private val takeVideoLauncher = registerForTakeVideoResult {
    if (uri != null && uri!!.size > 0) {
      PictureActivity.start(uri!!)
    }
  }

  private val cropPictureLauncher = registerForCropPictureResult { uri ->
    if (uri != null) {
      PictureActivity.start(uri)
    }
  }

  private val pickContentLauncher = registerForPickContentResult { uri ->
    if (uri != null) {
      PictureActivity.start(uri)
    }
  }

  private val getMultipleContentsLauncher = registerForGetMultipleContentsResult { uris ->
    if (uris.isNotEmpty()) {
      selector(uris.map { it.displayName!! }, getString(R.string.selected_files)) { _, i ->
        toast(uris[i].toString())
      }
    }
  }

  private val requestPermissionLauncher = registerForRequestPermissionResult(
    onGranted = {
      toast(R.string.read_permission_granted)
    },
    onDenied = {
      alert(getString(R.string.need_permission_title), getString(R.string.no_read_permission)) {
        okButton { launchAppSettings() }
        cancelButton()
      }
    },
    onShowRequestRationale = {
      alert(getString(R.string.need_permission_title), getString(R.string.need_read_permission)) {
        okButton { requestPermissionAgain() }
        cancelButton()
      }
    })

  private val requestMultiplePermissionsLauncher = registerForRequestMultiplePermissionsResult(
    onAllGranted = {
      toast(R.string.location_and_read_permissions_granted)
    },
    onDenied = { deniedList ->
      val message = when {
        deniedList.size == 2 -> R.string.need_location_and_read_permissions
        deniedList.first() == Manifest.permission.ACCESS_FINE_LOCATION -> R.string.need_location_permission
        else -> R.string.need_read_permission
      }
      alert(getString(message), getString(R.string.need_permission_title)) {
        launchAppSettings()
      }
    },
    onShowRequestRationale = { deniedList ->
      val message = when {
        deniedList.size == 2 -> R.string.need_location_and_read_permissions
        deniedList.first() == Manifest.permission.ACCESS_FINE_LOCATION -> R.string.need_location_permission
        else -> R.string.need_read_permission
      }
      alert(getString(message), getString(R.string.need_permission_title)) {
        requestDeniedPermissions()
      }
    })

  private val appSettingsLauncher = registerForLaunchAppSettingsResult {}

  private val enableBluetoothLauncher = registerForEnableBluetoothResult(
    onLocationDisabled = {
      alert(getString(R.string.location_disabled))
    },
    onBluetoothEnabled = { enabled ->
      if (enabled) {
        toast(R.string.bluetooth_enabled)
      } else {
        // 可弹框解释用途后可调用 enableBluetooth() 方法再次开启
      }
    })

  private val enableLocationLauncher = registerForEnableLocationResult { enabled ->
    if (enabled) {
      toast(R.string.location_enabled)
    } else {
      toast(R.string.location_disabled)
    }
  }

  private val createDocumentLauncher = registerForCreateDocumentResult { uri ->
    if (uri != null) {
      contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
        ?.use { cursor ->
          if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            toast("create $name")
          }
        }
    }
  }

  private val openDocumentLauncher = registerForOpenDocumentResult { uri ->
    if (uri != null) {
      contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
        ?.use { cursor ->
          if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            toast(name)
          }
        }
    }
  }

  private val openMultipleDocumentsLauncher = registerForOpenMultipleDocumentsResult { uris ->
    if (uris.isNotEmpty()) {
      val names = uris.map {
        contentResolver.query(it, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
          ?.use { cursor ->
            cursor.moveToFirst()
            cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
          }.orEmpty()
      }
      selector(names, getString(R.string.documents)) { _, _ -> }
    }
  }

  private val openDocumentTreeLauncher = registerForOpenDocumentTreeResult { uri ->
    if (uri != null) {
      val documentFile = DocumentFile.fromTreeUri(this, uri)!!
      val names = documentFile.listFiles().map { it.name.orEmpty() }
      selector(names, getString(R.string.documents)) { _, _ -> }
    }
  }

  private val pickContactLauncher = registerForPickContactResult { uri ->
    if (uri != null) {
      contentResolver.query(uri, arrayOf(ContactsContract.Data.DISPLAY_NAME), null, null, null)
        ?.use { cursor ->
          if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME))
            toast(name)
          }
        }
    }
  }

  private val inputTextLauncher = registerForActivityResult(InputTextResultContract()) {
    toast(it)
  }

  private val Uri.displayName: String?
    get() = contentResolver.query(this, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
      if (cursor.moveToFirst())
        cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
      else
        null
    }
}
