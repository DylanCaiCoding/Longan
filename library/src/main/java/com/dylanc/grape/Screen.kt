@file:Suppress("unused")

package com.dylanc.grape


/**
 * @author Dylan Cai
 */

inline val screenWith: Int get() = application.resources.displayMetrics.widthPixels

inline val screenHeight: Int get() = application.resources.displayMetrics.widthPixels

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