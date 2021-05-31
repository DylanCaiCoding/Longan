@file:Suppress("unused", "NOTHING_TO_INLINE", "MemberVisibilityCanBePrivate", "FunctionName")

package com.dylanc.longan

import android.graphics.Bitmap
import android.net.Uri
import com.dylanc.activityresult.launcher.TakePictureLauncher
import com.dylanc.activityresult.launcher.TakeVideoLauncher
import java.io.File

/**
 * @author Dylan Cai
 */

fun TakePictureLauncher.launch(onTakeSuccess: (Uri, String) -> Unit) {
  val pathname = "$externalCacheDirPath/${System.currentTimeMillis()}.jpg"
  launch(File(pathname)) { uri ->
    onTakeSuccess(uri, pathname)
  }
}

fun TakePictureLauncher.launch(pathname: String, onTakeSuccess: (Uri) -> Unit) {
  launch(File(pathname), onTakeSuccess)
}

fun TakePictureLauncher.launch(file: File, onTakeSuccess: (Uri) -> Unit) =
  file.toUri().let {
    launch(it) { takeSuccess ->
      if (takeSuccess)
        onTakeSuccess(it)
    }
  }

fun TakeVideoLauncher.launch(onTakeSuccess: (Uri, String, Bitmap?) -> Unit) {
  val pathname = "$externalCacheDirPath/${System.currentTimeMillis()}.mp4"
  launch(File(pathname)) { uri, bitmap ->
    onTakeSuccess(uri, pathname, bitmap)
  }
}

fun TakeVideoLauncher.launch(pathname: String, onTakeSuccess: (Uri, Bitmap?) -> Unit) {
  launch(File(pathname), onTakeSuccess)
}

fun TakeVideoLauncher.launch(file: File, onTakeSuccess: (Uri, Bitmap?) -> Unit) =
  file.toUri().let { uri ->
    launch(uri) {
      if (it != null) onTakeSuccess(uri, it)
    }
  }