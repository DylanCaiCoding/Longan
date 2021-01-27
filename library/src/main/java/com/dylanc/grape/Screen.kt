package com.dylanc.grape


val screenWith: Int get() = application.resources.displayMetrics.widthPixels

val screenHeight: Int get() = application.resources.displayMetrics.widthPixels

//var Activity.isFullScreen: Boolean
//  get() = TODO()
//  set(value) {
//    if (value) {
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//        window.insetsController?.hide(WindowInsets.Type.statusBars())
//      } else {
//        @Suppress("DEPRECATION")
//        window.setFlags(
//          WindowManager.LayoutParams.FLAG_FULLSCREEN,
//          WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
//      }
//    } else {
//
//    }
//  }