@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import android.os.Handler
import android.os.Looper

/**
 * @author Dylan Cai
 */

val mainHandler = lazy { Handler(Looper.getMainLooper()) }

inline fun mainThread(noinline block: () -> Unit) = mainHandler.value.post(block)

inline fun mainThread(delayMillis: Long, noinline block: () -> Unit) = mainHandler.value.postDelayed(block, delayMillis)