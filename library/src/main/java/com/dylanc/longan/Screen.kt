@file:Suppress("unused")

package com.dylanc.longan


/**
 * @author Dylan Cai
 */

inline val screenWidth: Int get() = application.resources.displayMetrics.widthPixels

inline val screenHeight: Int get() = application.resources.displayMetrics.heightPixels
